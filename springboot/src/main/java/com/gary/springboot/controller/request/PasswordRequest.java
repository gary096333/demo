package com.gary.springboot.controller.request;

import lombok.Data;

//修改密碼
@Data
public class PasswordRequest {
    private String username;
    private String password;
    private String newPass;
}
