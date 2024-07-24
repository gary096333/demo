package com.gary.springboot.mapper;

import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Borrow;
import com.gary.springboot.entity.Retur;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowMapper {

    List<Borrow> list();// 查詢數據庫所有資料

    List<Borrow> listByCondition(BaseRequest baseRequest);

    void save(Borrow obj);

    Borrow getById(Integer id);

    void updateById(Borrow user);

    void deleteById(Integer id);

    void updateStatus(String status, Integer id);// 通過id查詢更改

    List<Retur> listReturByCondition(BaseRequest baseRequest);

    void saveRetur(Retur obj);

    void deleteReturById(Integer id);

}
