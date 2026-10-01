package com.smzk.delivery_service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING_PAYMENT(1, "待付款"),
    PENDING_ACCEPT(2, "待接单"),
    ACCEPTED(3, "已接单"),
    DELIVERING(4, "派送中"),
    COMPLETED(5, "已完成"),
    CANCELLED(6, "已取消"),
    REFUNDED(7, "退款");

    @JsonValue
    @EnumValue
    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
