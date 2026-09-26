package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.entity.admin.Dish;
import com.smzk.delivery_service.vo.admin.DishQueryByIdVO;

import java.util.List;

public interface DishService extends IService<Dish> {

    List<DishQueryByIdVO> dishQueryByCategoryId(Integer categoryId);

    DishQueryByIdVO dishQueryById(Integer id);
}
