package com.smzk.delivery_service.exception;


import com.smzk.delivery_service.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handlerBusinessException(BusinessException e){
        log.error("业务异常",e);
        return Result.Failed(e.getMessage());
    }

    @ExceptionHandler
    public Result handlerDuplicateException(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        String[] messages = message.split(" ");
        String exactMessages = messages[messages.length - 1].replace("'","");
        if(exactMessages.equals("employee.user_name")){
            log.error("用户名重复");
            return Result.Failed("用户名重复");
        } else if (exactMessages.equals("employee.phone")) {
            log.error("手机号重复");
            return Result.Failed("手机号重复");
        }else if(exactMessages.equals("employee.identify_number")){
            log.error("身份证号重复");
            return Result.Failed("身份证号重复");
        }else if(exactMessages.equals("setmeal.name")){
            log.info("套餐名称重复");
            return Result.Failed("套餐名称重复");
        }
        return Result.Failed("未知错误");
    }
}
