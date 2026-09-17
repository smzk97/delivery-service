package com.smzk.delivery_service.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "oss.config")
public class OssConfig {
    private String endpoint;
    private String bucketName;
    private String region;
}
