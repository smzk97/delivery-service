package com.smzk.delivery_service.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Integer id;
    private String number;
    private Integer status;
    private Integer userId;
    private Integer addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private Integer payMethod;
    private Integer payStatus;
    private BigDecimal amount;
    private String remark;
    private String phone;
    private String address;
    private String consignee;
    private String cancelReason;
    private String rejectionReason;
    private LocalDateTime cancelTime;
    private LocalDateTime estimatedDeliveryTime;
    private Integer deliveryStatus;
    private LocalDateTime deliveryTime;
    private Integer packAmount;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
}
