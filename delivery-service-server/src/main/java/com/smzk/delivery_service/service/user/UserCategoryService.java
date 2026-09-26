package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.entity.admin.Category;

import java.util.List;

public interface UserCategoryService extends IService<Category> {
    List<Category> categoryQueryType(Integer type);
}
