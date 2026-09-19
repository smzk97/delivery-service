package com.smzk.delivery_service.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.dto.DishQueryPageDTO;
import com.smzk.delivery_service.entity.Dish;
import com.smzk.delivery_service.entity.DishQueryByIdVO;
import com.smzk.delivery_service.vo.PageResultVO;

import java.util.List;

public interface DishService extends IService<Dish> {
    void dishInsert(DishInsertDTO dishInsertDTO);

    void dishDelete(List<Integer> ids);

    DishQueryByIdVO dishQueryById(Integer id);

    List<Dish> dishQueryByCategoryId(Integer categoryId);

    PageResultVO dishQueryPage(DishQueryPageDTO dishQueryPageDTO);

    void dishConvertStatus(Integer status, Integer id);

    void dishUpdate(DishInsertDTO dishUpdateDTO);
}
