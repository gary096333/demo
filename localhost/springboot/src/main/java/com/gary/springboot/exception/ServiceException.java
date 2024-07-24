package com.gary.springboot.exception;

//自定義錯誤異常(給前端的錯誤信息)
//如直接接用RuntimeExceptiona沒用繼承會沒辦法知道哪裡出錯範圍太廣
public class ServiceException extends RuntimeException{

    private String code;
    public ServiceException(String message){
        super(message);
    }

    public String getCode()
    {
        return code;
    }

    public ServiceException(String code,String message){
        super(message);
        this.code=code;
    }

    //cause錯誤原因
    public ServiceException(String message,Throwable cause){
        super(message,cause);

    }


}
