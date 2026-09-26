package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.admin.SetmealDish;

import java.util.List;

public interface UserSetMealService extends IService<Setmeal> {
    List<Setmeal> setmealQueryByCategoryId(Integer categoryId);

    List<SetmealDish> setmealDishQueryById(Integer setmealId);
}
