package com.smzk.delivery_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitDTO {
    private Integer addressBookId;
    private BigDecimal amount;
    private Integer deliveryStatus;
    private String estimatedDeliveryTime;
    private BigDecimal packAmount;
    private Integer payMethod;
    private String remark;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
}
