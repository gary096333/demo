package com.gary.springboot.service;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {

    List<Category> list();

    PageInfo<Category> page(BaseRequest basePageRequest);

    void save(Category obj);

    Category getById(Integer id); 

    void update(Category obj);

    void deleteById(Integer id);
}
