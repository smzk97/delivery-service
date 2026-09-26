package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
public class UserCategoryController {

    private UserCategoryService userCategoryService;

    @Autowired
    UserCategoryController(UserCategoryService userCategoryService){
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("/list")
    public Result categoryQueryType(Integer type){
        List<Category> categories = userCategoryService.categoryQueryType(type);
        return Result.Success(categories);
    }

}
