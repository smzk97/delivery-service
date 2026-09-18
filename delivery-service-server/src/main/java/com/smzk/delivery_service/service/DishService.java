package com.smzk.delivery_service.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    void dishInsert(DishInsertDTO dishInsertDTO);

    void dishDelete(List<Integer> ids);
}
