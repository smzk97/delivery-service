package com.smzk.delivery_service.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserThreadLocal {
    private Integer id;
    private String openid;
}
