package com.gary.springboot.service;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IUserService {

    List<User> list();

    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id); //先查詢(修改)

    void update(User user);//再修改(修改)

    void deleteById(Integer id);

    void handleAccount(User user);
}
