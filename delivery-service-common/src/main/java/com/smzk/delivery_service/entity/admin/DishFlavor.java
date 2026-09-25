package com.smzk.delivery_service.entity.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("dish_flavor")
public class DishFlavor {
    private Integer id;
    private Integer dishId;
    private String name;
    private String value;
}
