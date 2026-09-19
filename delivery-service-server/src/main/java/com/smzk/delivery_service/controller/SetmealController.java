package com.smzk.delivery_service.controller;

import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.dto.SetmealQueryPageDTO;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.SetmealService;
import com.smzk.delivery_service.vo.PageResultVO;
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

    @RequestMapping("/page")
    public Result setmealQueryPage(SetmealQueryPageDTO setmealQueryPageDTO){
        PageResultVO pageResultVO = setmealService.setmealQueryPage(setmealQueryPageDTO);
        return Result.Success(pageResultVO);
    }

    @PutMapping("/status/{status}")
    public Result setmealConvertStatus(@PathVariable Integer status, Integer id){
        setmealService.setmealConvertStatus(status,id);
        return Result.Success();
    }
}
