package com.smzk.delivery_service.annotation;

import com.smzk.delivery_service.enums.OperateMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FixMessage {
    OperateMethod value();
}
