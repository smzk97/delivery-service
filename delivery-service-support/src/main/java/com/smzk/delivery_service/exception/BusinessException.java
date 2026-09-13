package com.smzk.delivery_service.exception;

import com.smzk.delivery_service.enums.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode,String msg){
        super(msg);
        this.errorCode = errorCode;
    }
}
