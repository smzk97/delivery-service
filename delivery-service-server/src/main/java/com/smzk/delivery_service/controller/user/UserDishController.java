package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.DishFlavor;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.DishService;
import com.smzk.delivery_service.vo.admin.DishQueryByIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
public class UserDishController {

    private DishService dishService;

    @Autowired
    UserDishController(DishService dishService){
        this.dishService = dishService;
    }

    @GetMapping("/list")
    public Result dishQueryByCategoryId(Integer categoryId){
        List<DishQueryByIdVO> dishes = dishService.dishQueryByCategoryId(categoryId);
        return Result.Success(dishes);
    }

    @GetMapping("/id")
    public Result dishQueryById(Integer id){
        DishQueryByIdVO dishes = dishService.dishQueryById(id);
        return Result.Success(dishes);
    }
}
