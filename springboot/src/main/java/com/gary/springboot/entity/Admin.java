package com.gary.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Admin {


    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createtime;
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date updatetime;
    //禁用功能
    //狀態(1:不禁用 true、 0:禁用 false)
    //數據庫是Integer類型，當前端傳true/false會自動轉1/0存入數據庫
    private boolean status;
}
