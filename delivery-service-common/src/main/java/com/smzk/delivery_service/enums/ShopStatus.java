package com.smzk.delivery_service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShopStatus {

    OPEN(1,"营业"),
    CLOSE(0,"打烊");

    @EnumValue
    @JsonValue
    private Integer code;
    private String description;

    ShopStatus(Integer code,String description){
        this.code = code;
        this.description =description;
    }
    public Integer getCode(){
        return this.code;
    }

    public String getDescription(){
        return this.description;
    }
}
