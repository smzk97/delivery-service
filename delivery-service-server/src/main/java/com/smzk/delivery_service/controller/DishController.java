package com.smzk.delivery_service.controller;

import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dish")
public class DishController {

    private DishService dishService;

    @Autowired
    public DishController(DishService dishService){
        this.dishService = dishService;
    }

    @PostMapping
    public Result dishInsert(@RequestBody DishInsertDTO dishInsertDTO){

        dishService.dishInsert(dishInsertDTO);
        return Result.Success();
    }

}
