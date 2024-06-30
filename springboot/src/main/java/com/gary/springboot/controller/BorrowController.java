package com.gary.springboot.controller;

import com.gary.springboot.common.Result;
import com.gary.springboot.controller.request.BorrowPageRequest;
import com.gary.springboot.entity.Borrow;
import com.gary.springboot.entity.Retur;
import com.gary.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController//標示這個接口為Rest風格
@RequestMapping("/borrow") //統一前綴路由名稱
public class BorrowController {

    @Autowired
    IBorrowService borrowService;


    @GetMapping("/list")  //次級路由名稱
    public Result list() {
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }


    @GetMapping("/page")//次級路由名稱
    public Result page(BorrowPageRequest pageRequest)
    {

        return Result.success(borrowService.page(pageRequest));
    }


    @PostMapping("/save")//次級路由名稱，新增
    public Result save(@RequestBody Borrow obj)
    {
        borrowService.save(obj);
        return Result.success();
    }

    @GetMapping("/{id}") //次級路由名稱，先查詢(修改)
    public Result getById(@PathVariable Integer id) {
        Borrow obj = borrowService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update") //次級路由名稱，再修改(修改)
    public Result update(@RequestBody Borrow obj) {
        borrowService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}") //次級路由名稱，刪除
    public Result deleteById(@PathVariable Integer id) {
        borrowService.deleteById(id);
        return Result.success();
    }


    @GetMapping("/pageRetur")//次級路由名稱
    public Result pageRetur(BorrowPageRequest pageRequest)
    {
        return Result.success(borrowService.pageRetur(pageRequest));
    }


    @PostMapping("/saveRetur")//次級路由名稱，新增
    public Result saveRetur(@RequestBody Retur obj)
    {
        borrowService.saveRetur(obj);
        return Result.success();
    }


    @DeleteMapping("/deleteRetur/{id}") //次級路由名稱，刪除
    public Result deleteRetur(@PathVariable Integer id) {
        borrowService.deleteReturById(id);
        return Result.success();
    }


}
