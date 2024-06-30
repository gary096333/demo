package com.gary.springboot.service.impl;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Category;
import com.gary.springboot.mapper.CategoryMapper;
import com.gary.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Category> categories = categoryMapper.listByCondition(baseRequest);//自關聯查詢(同一張表內的字段有所關聯性 id、pid)
        return  new PageInfo<>(categories);
    }

    @Override
    public void save(Category obj) {
        categoryMapper.save(obj);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category obj) {
        obj.setUpdatetime(LocalDate.now());  //更新時間
        categoryMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);

    }
}
