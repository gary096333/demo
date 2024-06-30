package com.gary.springboot.mapper;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    
    List<Category> list();//查詢數據庫所有資料

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void updateById(Category user);

    void deleteById(Integer id);
}
