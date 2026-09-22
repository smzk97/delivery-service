package com.smzk.delivery_service.controller.admin;

import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.dto.DishQueryPageDTO;
import com.smzk.delivery_service.entity.Dish;
import com.smzk.delivery_service.vo.DishQueryByIdVO;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.DishService;
import com.smzk.delivery_service.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping
    public Result dishDelete(@RequestParam List<Integer> ids){
        dishService.dishDelete(ids);
        return Result.Success();
    }

    @GetMapping("/{id}")
    public Result dishQueryById(@PathVariable Integer id){
        DishQueryByIdVO dish = dishService.dishQueryById(id);
        return Result.Success(dish);
    }

    @GetMapping("/list")
    public Result dishQueryByCategoryId(Integer categoryId){
        List<Dish> dishes = dishService.dishQueryByCategoryId(categoryId);
        return Result.Success(dishes);
    }

    @GetMapping("/page")
    public Result dishQueryPage(DishQueryPageDTO dishQueryPageDTO){
        PageResultVO pageResultVO = dishService.dishQueryPage(dishQueryPageDTO);
        return Result.Success(pageResultVO);
    }

    @PutMapping("/status/{status}")
    public Result dishConvertStatus(@PathVariable Integer status,Integer id){
        dishService.dishConvertStatus(status,id);
        return Result.Success();
    }

    @PutMapping
    public Result dishUpdate(@RequestBody DishInsertDTO dishUpdateDTO){
        dishService.dishUpdate(dishUpdateDTO);
        return Result.Success();
    }
}
