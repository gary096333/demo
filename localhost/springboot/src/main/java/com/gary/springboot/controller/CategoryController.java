package com.gary.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import com.gary.springboot.common.Result;

import com.gary.springboot.controller.request.CategoryPageRequest;
import com.gary.springboot.entity.Category;
import com.gary.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController//標示這個接口為Rest風格
@RequestMapping("/category") //統一前綴路由名稱
public class CategoryController {

    @Autowired
    ICategoryService categoryService;



    @GetMapping("/list")  //次級路由名稱
    public Result list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }


    @GetMapping("/page")//次級路由名稱
    public Result page(CategoryPageRequest pageRequest)
    {

        return Result.success(categoryService.page(pageRequest));
    }


    @PostMapping("/save")//次級路由名稱，新增
    public Result save(@RequestBody Category obj)
    {
        categoryService.save(obj);
        return  Result.success();
    }

    @GetMapping("/{id}") //次級路由名稱，先查詢(修改)
    public Result getById(@PathVariable Integer id){
        Category obj = categoryService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update") //次級路由名稱，再修改(修改)
    public Result update(@RequestBody Category obj){
        categoryService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}") //次級路由名稱，刪除
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }




    //Book category(分類)選項功能[級聯選擇]
    @GetMapping("/tree")
    public Result tree(){
        List<Category> list = categoryService.list();
        return Result.success(createTree(null,list)); //null表示從第一級別(父級別)開始遞歸
    }

    //遞歸樹
    private List<Category> createTree(Integer pid, List<Category> categories){
        List<Category> treeList = new ArrayList<>();//存放的容器
        for(Category category:categories){

            //如果pid和getPid都是null，代表是第一級別，開始遞歸
            if(pid ==null){
                if(category.getPid() ==null){

                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));

                }
            }else{

            if(pid.equals(category.getPid())){
                treeList.add(category);
                category.setChildren(createTree(category.getId(), categories));
            }

            //如果最後集合是空的，設置null值
            //children是集合，當沒值，設置null值
            if(CollUtil.isEmpty(category.getChildren())){
                category.setChildren(null);
            }
            }

        }
        return  treeList;
    }

    
}
