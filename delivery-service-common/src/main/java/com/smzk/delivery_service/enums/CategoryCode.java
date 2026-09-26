package com.smzk.delivery_service.enums;

public enum CategoryCode {
    DISH(1,"菜品"),
    SETMEAL(2,"套餐");

    private Integer code;
    private String msg;

    CategoryCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode(Integer code){
        return this.code;
    }
    public String getMsg(String msg){
        return this.msg;
    }
}
