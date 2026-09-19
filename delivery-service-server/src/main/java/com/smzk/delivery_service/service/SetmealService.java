package com.smzk.delivery_service.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.entity.Setmeal;
import com.smzk.delivery_service.vo.SetmealQueryByIdVO;

public interface SetmealService extends IService<Setmeal> {
    void setmealInsert(MealInsertDTO mealInsertDTO);

    SetmealQueryByIdVO setmealQueryById(Integer id);
}
