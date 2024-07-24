package com.gary.springboot.common;

import lombok.Data;

/**
 * 返回統一結果的包裝
 * Result包裝後台數據，前端都可透過 Result中的對象(code、data、msg)拿數據
 */

@Data
public class Result {

    private String code;//存放成功/失敗信息
    private Object data;//存放數據
    private String msg;//存放錯誤訊息


    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "1";



    public static Result success()
    {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static Result success(Object data)
    {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static Result error(String msg)
    {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;

    }

    public static Result error(String code,String msg){

    Result result = new Result();
    result.setCode(code);
    result.setMsg(msg);
    return result;
    }

}
