package com.smzk.deliveryservicesupport.exception;


import com.smzk.deliveryservicecommon.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handlerBusinessException(BusinessException e){
        log.error("业务异常",e);
        return Result.Failed(e.getMessage());
    }

}
