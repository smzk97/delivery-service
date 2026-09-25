package com.smzk.delivery_service.vo.admin;

import com.smzk.delivery_service.entity.admin.Dish;
import com.smzk.delivery_service.entity.admin.DishFlavor;
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
