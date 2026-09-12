package com.smzk.deliveryservicecommon.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {
    NORMAL(1,"正常"),
    LOCK(0,"锁定");

    @EnumValue
    @JsonValue
    private Integer num;
    private String msg;

    AccountStatus(Integer num,String msg){
        this.num = num;
        this.msg = msg;
    }

    public Integer getNum(){
        return this.num;
    }

    public String getMsg(){
        return this.msg;
    }
}
