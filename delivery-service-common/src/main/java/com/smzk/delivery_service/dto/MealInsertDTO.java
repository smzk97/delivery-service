package com.smzk.delivery_service.dto;

import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.admin.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealInsertDTO extends Setmeal {
    private List<SetmealDish> setmealDishes;
}
