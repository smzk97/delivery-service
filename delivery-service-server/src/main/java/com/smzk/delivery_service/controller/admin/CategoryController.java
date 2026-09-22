package com.smzk.delivery_service.controller.admin;

import com.smzk.delivery_service.dto.CategoryInsertDTO;
import com.smzk.delivery_service.dto.CategoryQueryPageDTO;
import com.smzk.delivery_service.entity.Category;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.CategoryService;
import com.smzk.delivery_service.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PutMapping
    public Result categoryUpdate(@RequestBody CategoryInsertDTO categoryUpdateDTO){
        categoryService.categoryUpdate(categoryUpdateDTO);
        return Result.Success();
    }

    @GetMapping("/page")
    public Result categoryQueryPage(CategoryQueryPageDTO categoryQueryPageDTO){
        PageResultVO categories = categoryService.categoryQueryPage(categoryQueryPageDTO);
        return Result.Success(categories);
    }

    @PostMapping("/status/{status}")
    public Result categoryConvertStatus(@PathVariable Integer status,Integer id){
        categoryService.categoryConvertStatus(status,id);
        return Result.Success();
    }

    @PostMapping
    public Result categoryInsert(@RequestBody CategoryInsertDTO categoryInsertDTO){
        categoryService.categoryInsert(categoryInsertDTO);
        return Result.Success();
    }

    @DeleteMapping
    public Result categoryDelete(Integer id){
        categoryService.categoryDelete(id);
        return Result.Success();
    }

    @GetMapping("/list")

    public Result categoryQueryType(Integer type){
        List<Category> categories = categoryService.categoryQueryType(type);
        return Result.Success(categories);
    }
}
