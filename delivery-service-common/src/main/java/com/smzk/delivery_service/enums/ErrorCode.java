package com.smzk.delivery_service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    SUCCESS(200,"操作成功"),
    PARAM_ERROR(400,"参数错误"),
    UNAUTHORIZED(401,"未登录或token过期"),
    LOCK(402,"账号被封禁"),
    FORBIDDEN(403,"无访问权限"),
    NOT_FOUND(404,"资源不存在"),
    BUSINESS_FAIL(500,"系统业务异常");

    @EnumValue
    @JsonValue
    private Integer code;
    private String msg;

    ErrorCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
