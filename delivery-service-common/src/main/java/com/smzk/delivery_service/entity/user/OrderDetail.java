package com.smzk.delivery_service.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`order_detail`")
public class OrderDetail {
    private Integer id;
    private String name;
    private String image;
    private Integer orderId;
    private Integer dishId;
    private Integer setmealId;
    private String dishFlavor;
    private Integer number;
    private BigDecimal amount;
    private Integer status;
}
