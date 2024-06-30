package com.gary.springboot.controller.request;


import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest{

    private String name;//搜索欄上的名稱
    private String phone;//搜索欄上的聯繫電話


}
