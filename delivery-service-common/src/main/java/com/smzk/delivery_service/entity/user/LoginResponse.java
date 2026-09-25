package com.smzk.delivery_service.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("secret_key")
    private String secretKey;
    private String unionid;
    private String openid;
    private Integer errcode;
    private String errmsg;
}
