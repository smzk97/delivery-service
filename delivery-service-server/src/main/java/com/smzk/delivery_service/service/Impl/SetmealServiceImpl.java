package com.smzk.delivery_service.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.entity.Category;
import com.smzk.delivery_service.entity.Setmeal;
import com.smzk.delivery_service.entity.SetmealDish;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.service.SetmealService;
import com.smzk.delivery_service.vo.SetmealQueryByIdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<BaseMapper<Setmeal>,Setmeal> implements SetmealService {

    @Override
    public void setmealInsert(MealInsertDTO mealInsertDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(mealInsertDTO,setmeal);
        this.save(setmeal);

        Integer id = setmeal.getId();
        List<SetmealDish> setmealDish = mealInsertDTO.getSetmealDishes();
        setmealDish.forEach(meal->meal.setSetmealId(id));
        Db.saveBatch(setmealDish);
    }

    @Override
    public SetmealQueryByIdVO setmealQueryById(Integer id) {
        Setmeal setmeal = this.getById(id);
        if(setmeal == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"查询为空");
        }
        SetmealQueryByIdVO setmealQueryByIdVO = new SetmealQueryByIdVO();
        BeanUtils.copyProperties(setmeal,setmealQueryByIdVO);

        Integer categoryId = setmeal.getCategoryId();
        Category one = Db.lambdaQuery(Category.class).eq(Category::getId, categoryId).one();
        if(one == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"套餐未关联分类");
        }
        setmealQueryByIdVO.setCategoryName(one.getName());

        List<SetmealDish> setmealDishes = Db.lambdaQuery(SetmealDish.class).eq(SetmealDish::getSetmealId,setmeal.getId()).list();
        setmealQueryByIdVO.setSetmealDishes(setmealDishes);

        return setmealQueryByIdVO;
    }
}
