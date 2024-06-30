package com.gary.springboot.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.gary.springboot.controller.dto.LoginDTO;
import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.LoginRequest;
import com.gary.springboot.controller.request.PasswordRequest;
import com.gary.springboot.entity.Admin;

import com.gary.springboot.exception.ServiceException;
import com.gary.springboot.mapper.AdminMapper;

import com.gary.springboot.service.IAdminService;

import com.gary.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASSWORD ="123";   //默認密碼
    private static final String PASS_SALT ="gingge"; //防止密碼被破解



    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Admin> users = adminMapper.listByCondition(baseRequest);
        return  new PageInfo<>(users);
    }

    @Override
    public void save(Admin obj) {
        //默認密碼
        if(StrUtil.isBlank(obj.getPassword())){ //如沒設置密碼就用默認密碼
        obj.setPassword(DEFAULT_PASSWORD);
        }

        //將密碼進行加密
        //透過SecureUtil 設置md5加密
        //PASS_SALT 防止密碼被破解
        obj.setPassword(securePassword(obj.getPassword()));
        try{//認為下面代碼會出錯，因用戶名不能重複(username唯一的)，不然傳錯誤
        adminMapper.save(obj);
        }catch(DuplicateKeyException e){//檢測出是針對DuplicateKeyException的錯誤

            log.error("數據新增失敗, username: {}",obj.getUsername(),e);
            throw new ServiceException("用戶名重複");



        }

    }

    @Override //先查詢(修改)
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override//再修改(修改)
    public void update(Admin user) {
        user.setUpdatetime(new Date());   //自動設置更新時間
        adminMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {
      
        //查詢用戶名是否存在
        //實現不出現同個用戶名，數據庫username加上unique(造成一個用戶名有多個密碼)
        Admin admin = null;
        try{//認為下面代碼會出錯，因用戶名不能重複(username唯一的)，不然傳錯誤
        admin = adminMapper.getByUserName(request.getUsername());
        }
        catch(Exception e){
            log.error("根據用戶名{}查詢出錯",request.getUsername());
            throw new ServiceException("用戶名錯誤");
        }


        //當輸入的帳密是數據庫沒有的或不相同的值
        if(admin==null){
            throw new ServiceException("用戶名或密碼錯誤");
        }

        //再判斷密碼是否合法
        //login也需使用與數據庫相同的加密後密碼進行登入
        String securePass = securePassword(request.getPassword()); //進行加密
        if(!securePass.equals(admin.getPassword())){
            throw new ServiceException("用戶名或密碼錯誤");
        }


        //禁用帳戶，禁用後無法登入
        //判斷status是否不是true，false的話無法登入
        if(!admin.isStatus()){
            throw new ServiceException("當前用戶處於禁用狀態，請聯繫管理員");
        }

        LoginDTO loginDTO= new LoginDTO();
        //BeanUtils.copyProperties()將Admin類中的屬性轉為LoginDTO類
        //只能將LoginDTO類與Admin類中一樣的屬性進行複製，將Admin類中的屬性變為LoginDTO類
        BeanUtils.copyProperties(admin,loginDTO);//當這裡輸入資料庫沒有的帳密會報異常


        //生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginDTO.setToken(token);

        return loginDTO;
    }



    //密碼加密封裝方法
    private String securePassword(String password){

    return SecureUtil.md5(password+PASS_SALT);
    }

    //修改密碼
    @Override
    public void changePass(PasswordRequest request) {
        //新密碼要進行加密
        request.setNewPass(securePassword(request.getNewPass()));
       int count =adminMapper.updatePassword(request);
       if(count <= 0){
           throw new ServiceException("修改密碼失敗");
       }


    }



}
