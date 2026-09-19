package com.smzk.delivery_service.controller;

import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.SetmealService;
import com.smzk.delivery_service.vo.SetmealQueryByIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    private SetmealService setmealService;

    @Autowired
    public SetmealController(SetmealService setmealService){
        this.setmealService = setmealService;
    }

    @PostMapping
    public Result setmealInsert(@RequestBody MealInsertDTO mealInsertDTO){
        setmealService.setmealInsert(mealInsertDTO);
        return Result.Success();
    }

    @RequestMapping("/{id}")
    public Result setmealQueryById(@PathVariable Integer id){
        SetmealQueryByIdVO setmealQueryByIdVO = setmealService.setmealQueryById(id);
        return Result.Success(setmealQueryByIdVO);
    }

}
