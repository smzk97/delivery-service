package com.smzk.delivery_service.exception;


import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.enums.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    /**
     * 1. 拦截 @RequestBody JSON 校验失败（最常见）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("请求体参数校验失败");
        return Result.Failed(msg);
    }

    /**
     * 2. 拦截表单 / @ModelAttribute 对象校验失败
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String msg = e.getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("表单参数绑定失败");
        return Result.Failed(msg);
    }

    /**
     * 3. 拦截 @RequestParam / @PathVariable / Service 层方法校验失败
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolation(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("参数校验有误");
        return Result.Failed(msg);
    }
}
