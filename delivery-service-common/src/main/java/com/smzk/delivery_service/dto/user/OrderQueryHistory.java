package com.smzk.delivery_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryHistory {
    private Integer page;
    private Integer pageSize;
    private Integer status;
}
