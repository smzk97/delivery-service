package com.smzk.delivery_service.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("setmeal")
public class SetmealDish {
    private Integer id;
    private Integer setmealId;
    private Integer dishId;
    private String name;
    private BigDecimal price;
    private Integer copies;
}
