package com.gary.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

//通用實現類(常用到的字段名稱)
//Serializable唯一序列化
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID =1L;


    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createtime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate updatetime;

}
