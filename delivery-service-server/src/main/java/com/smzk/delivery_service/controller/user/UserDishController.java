package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.UserDishService;
import com.smzk.delivery_service.vo.admin.DishQueryByIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
public class UserDishController {

    private UserDishService userDishService;

    @Autowired
    UserDishController(UserDishService userDishService){
        this.userDishService = userDishService;
    }

    @GetMapping("/list")
    public Result dishQueryByCategoryId(Integer categoryId){
        List<DishQueryByIdVO> dishes = userDishService.dishQueryByCategoryId(categoryId);
        return Result.Success(dishes);
    }

    @GetMapping("/id")
    public Result dishQueryById(Integer id){
        DishQueryByIdVO dishes = userDishService.dishQueryById(id);
        return Result.Success(dishes);
    }
}
