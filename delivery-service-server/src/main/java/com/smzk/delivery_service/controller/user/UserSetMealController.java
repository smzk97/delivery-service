package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.admin.SetmealDish;
import com.smzk.delivery_service.service.user.UserSetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
public class UserSetMealController {

    private UserSetMealService userSetMealService;

    @Autowired
    UserSetMealController(UserSetMealService userSetMealService){
        this.userSetMealService = userSetMealService;
    }

    @GetMapping("/list")
    public Result setmealQueryByCategoryId(Integer categoryId){
        List<Setmeal> setmeals = userSetMealService.setmealQueryByCategoryId(categoryId);
        return Result.Success(setmeals);
    }

    @GetMapping("/dish")
    public Result setmealDishQueryById(Integer setmealId){
        List<SetmealDish> setmealDishes =  userSetMealService.setmealDishQueryById(setmealId);
        return Result.Success(setmealDishes);
    }

}
