package com.gary.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Data
public class User {

    private Integer id;
    private String name;
    private String username;//會員編號
    private Integer age;
    private String sex;
    private String phone;
    private String address;


    //"yyyy-MM-dd" 注意不能有空格
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatetime;

    private Integer account;//當前帳戶積分 //充值

    private Integer score; //數據庫無此字段(與數據庫無相關) //充值

    //狀態(1:不禁用 true、 0:禁用 false)
    //數據庫是Integer類型，當前端傳true/false會自動轉1/0存入數據庫
    private boolean status;

}
