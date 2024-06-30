package com.gary.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Book;
import com.gary.springboot.exception.ServiceException;
import com.gary.springboot.mapper.BookMapper;
import com.gary.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Book> list = bookMapper.listByCondition(baseRequest);
        return  new PageInfo<>(list);
    }

    @Override
    public void save(Book obj) {

        try{
        obj.setCategory(category(obj.getCategories()));//Book category(分類)選項功能，級聯選擇
        bookMapper.save(obj);

        }catch(Exception e){
            throw new ServiceException("數據插入錯誤",e);

        }


    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {

        try{
        obj.setCategory(category(obj.getCategories()));//Book category(分類)選項功能，級聯選擇

        obj.setUpdatetime(LocalDate.now());  //更新時間
        bookMapper.updateById(obj);

        }catch(Exception e){
            throw new ServiceException("數據更新錯誤",e);

        }



    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);

    }

    //Book category(分類)選項功能，級聯選擇
    private String category(List<String> categories){
        StringBuilder sb = new StringBuilder();
        if(CollUtil.isNotEmpty(categories)){
            categories.forEach(v ->sb.append(v).append(">"));
            return sb.substring(0,sb.lastIndexOf(">"));

        }
        return sb.toString();
    }




}
