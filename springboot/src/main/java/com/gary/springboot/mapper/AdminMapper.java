package com.gary.springboot.mapper;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.LoginRequest;
import com.gary.springboot.controller.request.PasswordRequest;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.Admin;
import com.gary.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {


    List<Admin> list();//查詢數據庫所有資料

    List<Admin> listByCondition(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void updateById(Admin user);

    void deleteById(Integer id);

    //登入
    //Admin getByUsernameAndPassword(@Param("username") String username,@Param("password") String password);  //獲得Admin對象
    Admin getByUserName(String username);//實現不出現同個用戶名(造成一個用戶名有多個密碼)


    int updatePassword(PasswordRequest request);


}
