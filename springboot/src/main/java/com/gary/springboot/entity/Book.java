package com.gary.springboot.entity;


import lombok.Data;

import java.io.Serializable;

import java.util.List;

@Data
public class Book extends BaseEntity  {
    private  static final long serialVersionUID =1L;

    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String category;
    private String bookNo;
    private String cover;

    private List<String>categories;//可返回前端數據 categories數組

    private Integer score;
    private Integer nums;




}
