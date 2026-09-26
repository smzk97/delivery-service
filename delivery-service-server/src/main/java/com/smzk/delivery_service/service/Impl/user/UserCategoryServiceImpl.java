package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.mapper.admin.CategoryMapper;
import com.smzk.delivery_service.service.user.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryServiceImpl extends ServiceImpl<BaseMapper<Category>,Category> implements UserCategoryService {

    @Override
    public List<Category> categoryQueryType(Integer type) {
        return this.list(new LambdaUpdateWrapper<Category>().eq(Category::getType,type));
    }
}
