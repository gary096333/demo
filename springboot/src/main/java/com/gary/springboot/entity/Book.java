package com.gary.springboot.entity;

import lombok.Data;

import java.io.Serializable;

import java.util.List;

@Data
public class Book extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String category;// 與數據庫傳輸
    private String bookNo;
    private String cover;

    private List<String> categories;// 前後端傳輸List<String>類型的數據 categories

    private Integer score;
    private Integer nums;

}
