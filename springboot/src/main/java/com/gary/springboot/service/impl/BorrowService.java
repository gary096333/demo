package com.gary.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.gary.springboot.controller.request.BaseRequest;
import com.gary.springboot.entity.Book;
import com.gary.springboot.entity.Borrow;
import com.gary.springboot.entity.Retur;
import com.gary.springboot.entity.User;
import com.gary.springboot.exception.ServiceException;
import com.gary.springboot.mapper.BookMapper;
import com.gary.springboot.mapper.BorrowMapper;
import com.gary.springboot.mapper.UserMapper;

import com.gary.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);

        // 還書提醒
        for (Borrow borrow : borrows) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();

            if (now.plusDays(1).isEqual(returnDate)) {// 當前日期+1=歸還日期，代表剩下1天就需還書
                borrow.setNote("即將到期");
            } else if (now.equals(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已過期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    @Transactional // 保證數據安全性 //添加事務保證數據同時同步進行
    public void save(Borrow obj) {// 前端傳過來的數據在數據庫不一定有，所以先進行查詢判斷
        // 1.用戶是否存在
        String userNo = obj.getUserNo();
        User user = userMapper.getByUsername(userNo);// 進行查詢
        if (Objects.isNull(user)) {
            throw new ServiceException("用戶不存在");
        }

        // 2.圖書是否存在
        Book book = bookMapper.getbyNo(obj.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借圖書不存在");
        }

        Integer account = user.getAccount();
        Integer score = book.getScore() * obj.getDays(); // score=借一本書的積分*天數
        obj.setScore(score);

        // 判斷圖書數量
        if (book.getNums() < 1) {
            throw new ServiceException("圖書數量不足");
        }

        // 判斷積分是否足夠
        if (score > account) {
            throw new ServiceException("用戶積分不足");
        }

        // 3.扣除用戶積分(先扣積分才借)
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);

        // 4.扣除圖書的數量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

        // 當前日期+days(借書天數)=歸還日期
        // ChronoUnit.DAYS 單位
        obj.setReturnDate(LocalDate.now().plus(obj.getDays(), ChronoUnit.DAYS));

        // 5.新增借書紀錄
        borrowMapper.save(obj);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {
        obj.setUpdatetime(LocalDate.now()); // 更新時間
        borrowMapper.updateById(obj);

    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);

    }

    // ================================================

    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listReturByCondition(baseRequest));
    }

    @Override
    @Transactional
    public void saveRetur(Retur obj) {
        // 1.還書後，改狀態
        obj.setStatus("已歸還");// 針對還書(retur)頁面改狀態
        borrowMapper.updateStatus("已歸還", obj.getId());// 針對借書(borrow)頁面改狀態

        obj.setRealDate(LocalDate.now());// 設置實際歸還日期

        borrowMapper.saveRetur(obj);

        // 2.圖書數量增加
        bookMapper.updateNumByNo(obj.getBookNo());

        // 3.當提早還書，需返回用戶積分；當超過時間還書，扣除積分，另外如果積分扣到負數，帳戶會被禁用
        Book book = bookMapper.getbyNo(obj.getBookNo());
        if (book != null) {
            long until = 0;

            if (obj.getRealDate().isBefore(obj.getReturnDate())) {// 判斷實際還書日期有無在還書日期之前(有無提前還書)
                until = obj.getRealDate().until(obj.getReturnDate(), ChronoUnit.DAYS);// 計算提前還書的天數差(提早還書)

            } else if (obj.getRealDate().isAfter(obj.getReturnDate())) {
                until = -obj.getReturnDate().until(obj.getRealDate(), ChronoUnit.DAYS);// 計算逾期還書的天數差(時間超過才還書，要扣積分)

            }
            int score = (int) until * book.getScore(); // 計算返回用戶積分(負數代表超過時間才還書)

            User user = userMapper.getByUsername(obj.getUserNo());// 根據用戶標準碼查詢用戶
            int account = user.getAccount() + score;// 將計算機分結果加到帳戶
            user.setAccount(account);

            if (account < 0) {// 當用戶帳戶為負數，代表無積分扣除，禁用帳戶

                user.setStatus(false);// 將帳戶設置禁用

            }

            userMapper.updateById(user);// 更新用戶積分

        }
    }

    @Override
    public void deleteReturById(Integer id) {
        borrowMapper.deleteReturById(id);
    }

}
