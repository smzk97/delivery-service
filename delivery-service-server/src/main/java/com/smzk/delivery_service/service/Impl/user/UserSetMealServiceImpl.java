package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.admin.SetmealDish;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.service.user.UserSetMealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSetMealServiceImpl extends ServiceImpl<BaseMapper<Setmeal>,Setmeal> implements UserSetMealService {

    @Override
    public List<Setmeal> setmealQueryByCategoryId(Integer categoryId) {
        if(categoryId == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        List<Setmeal> setmeals = this.list(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId,categoryId)).stream().toList();
        return setmeals;
    }

    @Override
    public List<SetmealDish> setmealDishQueryById(Integer setmealId) {
        List<SetmealDish> lists = Db.lambdaQuery(SetmealDish.class).eq(SetmealDish::getSetmealId,setmealId).list();
        return lists;
    }
}
