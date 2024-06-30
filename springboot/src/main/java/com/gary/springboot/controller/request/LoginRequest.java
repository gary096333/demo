package com.gary.springboot.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//登入

@Data

public class LoginRequest {
    private String username;
    private String password;
}
