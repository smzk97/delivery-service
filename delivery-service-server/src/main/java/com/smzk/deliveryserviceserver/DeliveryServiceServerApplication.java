package com.smzk.deliveryserviceserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.smzk.deliveryserviceserver.mapper")
public class DeliveryServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceServerApplication.class, args);
    }

}
