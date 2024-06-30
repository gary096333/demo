package com.gary.springboot.service;

import com.gary.springboot.controller.dto.LoginDTO;
import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.LoginRequest;
import com.gary.springboot.controller.request.PasswordRequest;
import com.gary.springboot.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAdminService {

    List<Admin> list();

   PageInfo<Admin> page(BaseRequest basePageRequest);

    void save(Admin obj);

    Admin getById(Integer id); 

    void update(Admin obj);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest request);

    void changePass(PasswordRequest request);
}
