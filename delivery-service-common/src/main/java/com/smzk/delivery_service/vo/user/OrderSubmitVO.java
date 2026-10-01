package com.smzk.delivery_service.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmitVO {
    private Integer id;
    private BigDecimal orderAmount;
    private String orderNumber;
    private LocalDateTime orderTime;
}
