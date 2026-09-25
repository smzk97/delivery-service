package com.smzk.delivery_service.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "wx.config")
public class LoginParam {
    private String appid;
    private String secret;
    @JsonProperty("js_code")
    private String jsCode;
    @JsonProperty("grant_type")
    private String grantType;
}
