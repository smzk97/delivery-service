package com.smzk.delivery_service;

import com.smzk.delivery_service.entity.OssConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.smzk.delivery_service.mapper")
@EnableConfigurationProperties(OssConfig.class)
public class DeliveryServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceServerApplication.class, args);
    }

}
