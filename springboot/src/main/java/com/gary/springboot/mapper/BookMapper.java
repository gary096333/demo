package com.gary.springboot.mapper;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    
    List<Book> list();//查詢數據庫所有資料

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void updateById(Book user);

    void deleteById(Integer id);

    Book getbyNo(String bookNo);

    //還書後更新圖書數量
    //根據 bookNo
    void updateNumByNo(String bookNo);



}
