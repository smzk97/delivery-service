package com.smzk.delivery_service.vo;

import com.smzk.delivery_service.entity.Setmeal;
import com.smzk.delivery_service.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetmealQueryByIdVO extends Setmeal {
    private String categoryName;
    private List<SetmealDish> setmealDishes;
}
