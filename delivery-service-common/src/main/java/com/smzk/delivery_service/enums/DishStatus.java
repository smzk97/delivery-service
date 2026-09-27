package com.smzk.delivery_service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DishStatus {

    OPEN(1,"起售"),
    CLOSE(0,"停售");

    @EnumValue
    @JsonValue
    private Integer code;
    private String description;

    DishStatus(Integer code,String description){
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
