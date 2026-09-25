package com.smzk.delivery_service.enums;


public enum SexCode {
    FEMALE(0,"女生"),
    MALE(1,"男生");

    private Integer code;
    private String msg;

    SexCode(Integer code,String msg){
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
