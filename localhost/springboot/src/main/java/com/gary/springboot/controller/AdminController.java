package com.gary.springboot.controller;

import com.gary.springboot.common.Result;
import com.gary.springboot.controller.dto.LoginDTO;
import com.gary.springboot.controller.request.AdminPageRequest;
import com.gary.springboot.controller.request.LoginRequest;
import com.gary.springboot.controller.request.PasswordRequest;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.Admin;
import com.gary.springboot.service.IAdminService;
import com.gary.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController//標示這個接口為Rest風格
@RequestMapping("/admin") //統一前綴路由名稱
public class AdminController {

    @Autowired
    IAdminService adminService;


    @GetMapping("/list")  //次級路由名稱
    public Result list() {
        List<Admin> list = adminService.list();
        return Result.success(list);
    }


    @GetMapping("/page")//次級路由名稱
    public Result page(AdminPageRequest pageRequest)
    {

        return Result.success(adminService.page(pageRequest));
    }


    @PostMapping("/save")//次級路由名稱，新增
    public Result save(@RequestBody Admin obj)
    {
        adminService.save(obj);
        return  Result.success();
    }

    @GetMapping("/{id}") //次級路由名稱，先查詢(修改)
    public Result getById(@PathVariable Integer id){
        Admin obj = adminService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update") //次級路由名稱，再修改(修改)
    public Result update(@RequestBody Admin obj){
        adminService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}") //次級路由名稱，刪除
    public Result deleteById(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    //登入
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        LoginDTO lo = adminService.login(request);
        return Result.success(lo);
    }

    //修改密碼
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest request){
        adminService.changePass(request);
        return Result.success();

    }
}
