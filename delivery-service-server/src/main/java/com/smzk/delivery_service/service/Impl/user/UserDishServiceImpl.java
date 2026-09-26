package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.entity.admin.Dish;
import com.smzk.delivery_service.entity.admin.DishFlavor;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.service.user.DishService;
import com.smzk.delivery_service.vo.admin.DishQueryByIdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserDishServiceImpl extends ServiceImpl<BaseMapper<Dish>,Dish> implements DishService {


    @Override
    public List<DishQueryByIdVO> dishQueryByCategoryId(Integer categoryId) {
        if(categoryId == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        List<Dish> dishes = this.list(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, categoryId));
        if(CollectionUtils.isEmpty(dishes)){
            return Collections.emptyList();
        }
        Category byId = Db.getById(categoryId, Category.class);
        String categoryName = byId.getName();
        List<Integer> ids = dishes.stream().map(Dish::getId).toList();
        Map<Integer,List<DishFlavor>> flavors = Db.lambdaQuery(DishFlavor.class).in(DishFlavor::getDishId,ids)
                .list().stream()
                .collect(Collectors.groupingBy(DishFlavor::getDishId));
        List<DishQueryByIdVO> dishQueryByIdVOS = dishes.stream().map(dish->{
            DishQueryByIdVO dishQueryByIdVO = new DishQueryByIdVO();
            BeanUtils.copyProperties(dish,dishQueryByIdVO);
            dishQueryByIdVO.setCategoryName(categoryName);
            dishQueryByIdVO.setFlavors(flavors.getOrDefault(dish.getId(), Collections.emptyList()));
            return dishQueryByIdVO;
        }).toList();
        return dishQueryByIdVOS;
    }
}
