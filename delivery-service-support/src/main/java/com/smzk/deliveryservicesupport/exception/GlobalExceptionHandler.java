package com.smzk.deliveryservicesupport.exception;


import com.smzk.deliveryservicecommon.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result globalException(Exception e){
        log.error("程序出错",e);
        return Result.Failed(e.toString());
    }

}
