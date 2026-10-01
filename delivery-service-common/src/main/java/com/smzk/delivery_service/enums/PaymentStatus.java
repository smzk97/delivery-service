package com.smzk.delivery_service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    UNPAID(0, "未支付"),
    PAID(1,"已支付"),
    REFUNDED(2, "退款");

    @JsonValue
    @EnumValue
    private final int code;
    private final String desc;

    PaymentStatus(int code, String desc) {
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
