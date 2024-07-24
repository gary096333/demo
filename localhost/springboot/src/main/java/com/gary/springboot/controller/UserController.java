package com.gary.springboot.controller;

import com.gary.springboot.common.Result;
import com.gary.springboot.controller.request.UserPageRequest;
import com.gary.springboot.entity.User;
import com.gary.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController//標示這個接口為Rest風格
@RequestMapping("/user") //統一前綴路由名稱
public class UserController {

    @Autowired
    IUserService userService;


    @GetMapping("/list")  //次級路由名稱
    public Result list() {
        List<User> users = userService.list();
        return Result.success(users);
    }


    @GetMapping("/page")//次級路由名稱
    public Result page(UserPageRequest userPageRequest)//不能寫BaseRequest，controller層規則，不然只會傳兩個值
    {

        return Result.success(userService.page(userPageRequest));
    }


    @PostMapping("/save")//次級路由名稱，新增
    public Result save(@RequestBody User user)//通過前端數據是json格式，透過@RequestBody轉為user對象
    {
        userService.save(user);
        return  Result.success();
    }

    @GetMapping("/{id}") //次級路由名稱，先查詢(修改)
    public Result getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PutMapping("/update") //次級路由名稱，再修改(修改)
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}") //次級路由名稱，刪除
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    //充值積分
    @PostMapping("/account")
    public Result account(@RequestBody User user){
        userService.handleAccount(user);
        return Result.success();
    }
}
