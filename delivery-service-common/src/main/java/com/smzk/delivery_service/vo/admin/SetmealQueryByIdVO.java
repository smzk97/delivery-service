package com.smzk.delivery_service.vo.admin;

import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.admin.SetmealDish;
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
