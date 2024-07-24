package com.gary.springboot.service;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {

    List<Book> list();

    PageInfo<Book> page(BaseRequest basePageRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);
}
