package com.gary.springboot.service;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Borrow;
import com.gary.springboot.entity.Retur;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IBorrowService {

    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest basePageRequest);

    void save(Borrow obj);

    Borrow getById(Integer id); 

    void update(Borrow obj);
    void deleteById(Integer id);





    PageInfo<Retur> pageRetur(BaseRequest basePageRequest);

    void saveRetur(Retur obj);


    void deleteReturById(Integer id);


    
}
