package com.gary.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class Borrow {

    private Integer id;
    private String bookName;
    private String bookNo;
    private String userNo;
    private String userName;
    private String userPhone;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private LocalDate createtime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate updatetime;

    private Integer score;

    private String status;
    private Integer days;
    private LocalDate returnDate;

    //提醒狀態(還書)
    //1.即將到期(-1)、2.已到期(當天)、3.已過期(超過還書時間之後) 4.正常
    private String note;



}
