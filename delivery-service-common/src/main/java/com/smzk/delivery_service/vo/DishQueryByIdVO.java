package com.smzk.delivery_service.vo;

import com.smzk.delivery_service.entity.Dish;
import com.smzk.delivery_service.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishQueryByIdVO extends Dish {
    private String categoryName;
    private List<DishFlavor> flavors;
}
