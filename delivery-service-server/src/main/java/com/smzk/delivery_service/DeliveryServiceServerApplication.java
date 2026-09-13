package com.smzk.delivery_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.smzk.delivery_service.mapper")
public class DeliveryServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceServerApplication.class, args);
    }

}
