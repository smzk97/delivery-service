package com.smzk.delivery_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.entity.Dish;
import com.smzk.delivery_service.entity.DishFlavor;
import com.smzk.delivery_service.entity.SetmealDish;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.mapper.DishMapper;
import com.smzk.delivery_service.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<BaseMapper<Dish>,Dish> implements DishService {

    private DishMapper dishMapper;

    @Autowired
    public DishServiceImpl(DishMapper dishMapper){
        this.dishMapper = dishMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dishInsert(DishInsertDTO dishInsertDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishInsertDTO,dish);
        this.save(dish);

        List<DishFlavor> flavors = dishInsertDTO.getFlavors();
        if(flavors.isEmpty()){
            return;
        }
        Integer id = dish.getId();
        flavors.forEach(flavor -> flavor.setDishId(id));
        Db.saveBatch(flavors);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dishDelete(List<Integer> ids) {
        if(ids.isEmpty()){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        List<Dish> dishes = this.listByIds(ids);
        if(dishes.isEmpty() || dishes.size() < ids.size()){
            throw new BusinessException(ErrorCode.NOT_FOUND,"菜品不存在");
        }
        dishes.forEach(dish->{
            if(dish.getStatus().equals(1)){
                throw new BusinessException(ErrorCode.BUSINESS_FAIL,"菜品未下架");
            }
        });
        List<SetmealDish> lists = Db.lambdaQuery(SetmealDish.class)
                .in(SetmealDish::getDishId, ids)
                .list();
        if(!lists.isEmpty()){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"菜品关联套餐");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> wrapper = Wrappers.lambdaQuery(DishFlavor.class)
                .in(DishFlavor::getDishId, ids);
        Db.remove(wrapper);
    }
}
