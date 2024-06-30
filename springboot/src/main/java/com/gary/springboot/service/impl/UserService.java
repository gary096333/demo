package com.gary.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.User;
import com.gary.springboot.mapper.UserMapper;
import com.gary.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service//為spring的組件，才能用@Autowired
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());//使用pom.xml pagehelper
        List<User> users = userMapper.listByCondition(baseRequest);
        return  new PageInfo<>(users);
    }

    @Override
    public void save(User user) {
        Date date =new Date();
        //生成卡號加上日期，userName當作卡號(防止添加重複名稱時，可添加時間號碼辨認)
        //將會員編號本來很常有英文加數字，縮短變為只有數字(用hashCode，返回的是純數字，可能是正數可能是負數，所以取絕對值Math.abs())
        user.setUsername(DateUtil.format(date, "yyyyMMdd")+ Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);

    }

    @Override //先查詢(修改)
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override//再修改(修改)
    public void update(User user) {
        user.setUpdatetime(new Date());   //自動設置更新時間
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    //充值
    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore(); //User的score跟數據庫無關聯
        if(score == null){
            return;
        }

        Integer id = user.getId();
        User dbUser = userMapper.getById(id); //根據用戶id，查詢關聯數據
        dbUser.setAccount(dbUser.getAccount()+ score);//更新當前帳戶積分(Account)+積分(score)
        userMapper.updateById(dbUser);//修改戶數據

    }
}
