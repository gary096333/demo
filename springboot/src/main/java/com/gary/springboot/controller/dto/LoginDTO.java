package com.gary.springboot.controller.dto;


import lombok.Data;

//封裝返回的信息
@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String token; //憑證

}
