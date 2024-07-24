package com.gary.springboot.exception;


import cn.hutool.core.util.StrUtil;
import com.gary.springboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//處理異常(全局異常)
//沒有捕獲到的錯誤都可在這裡捕獲
@Slf4j  //日誌
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionError(Exception e) {
        log.error("系統錯誤", e);
        return Result.error("系統錯誤");
    }

    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionError(ServiceException e) {
        log.error("業務異常", e);
        String code = e.getCode();
        if (StrUtil.isNotBlank(code)) {
            return Result.error(code, e.getMessage());
        }
        return Result.error(e.getMessage());
    }


}
