package com.smzk.delivery_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.dto.SetmealQueryPageDTO;
import com.smzk.delivery_service.entity.Category;
import com.smzk.delivery_service.entity.Setmeal;
import com.smzk.delivery_service.entity.SetmealDish;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.service.SetmealService;
import com.smzk.delivery_service.vo.PageResultVO;
import com.smzk.delivery_service.vo.SetmealQueryByIdVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

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

    @Override
    public PageResultVO setmealQueryPage(SetmealQueryPageDTO setmealQueryPageDTO) {
        IPage<Setmeal> iPage = new Page<>(setmealQueryPageDTO.getPage(),setmealQueryPageDTO.getPageSize());
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<Setmeal>()
                .select(Setmeal::getId)
                .like(StringUtils.hasText(setmealQueryPageDTO.getName()),Setmeal::getName,setmealQueryPageDTO.getName())
                .eq(setmealQueryPageDTO.getCategoryId() != null,Setmeal::getCategoryId,setmealQueryPageDTO.getCategoryId())
                .eq(setmealQueryPageDTO.getStatus() != null,Setmeal::getStatus,setmealQueryPageDTO.getStatus());
        IPage<Setmeal> lists = this.page(iPage,lambdaQueryWrapper);
        List<Setmeal> meals = lists.getRecords();
        if(CollectionUtils.isEmpty(meals)){
            return new PageResultVO(lists.getTotal(),meals);
        }
        List<Integer> ids = meals.stream().map(Setmeal::getId).toList();
        List<Setmeal> datas = this.listByIds(ids);
        return new PageResultVO(lists.getTotal(),datas);
    }

    @Override
    public void setmealConvertStatus(Integer status,Integer id) {
        Setmeal setmeal = this.getById(id);
        if(setmeal == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"该套餐不存在");
        }
        Setmeal setmeal1 = Setmeal.builder().id(id).build();
        if(!setmeal.getStatus().equals(status)){
            this.update(setmeal1,new LambdaUpdateWrapper<Setmeal>().set(Setmeal::getStatus,status));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setmealDelete(List<Integer> ids) {
        long count = Db.lambdaQuery(Setmeal.class).in(Setmeal::getId,ids).count();
        if(count < ids.size()){
            throw new BusinessException(ErrorCode.NOT_FOUND,"套餐不存在");
        }
        List<Setmeal> setmeals = this.listByIds(ids);
        setmeals.forEach(meal->{
            if(meal.getStatus().equals(1)){
                throw new BusinessException(ErrorCode.BUSINESS_FAIL,"套餐未禁用");
            }
        });
        this.removeByIds(ids);
        Db.remove(Wrappers.lambdaQuery(SetmealDish.class).in(SetmealDish::getSetmealId,ids));
    }

    @Override
    public void setmealUpdate(MealInsertDTO mealInsertDTO) {
        Integer id = mealInsertDTO.getId();
        if(id == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"缺失id");
        }

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(mealInsertDTO,setmeal);
        this.updateById(setmeal);

        Db.remove(Wrappers.lambdaQuery(SetmealDish.class).eq(SetmealDish::getSetmealId,id));
        List<SetmealDish> dishes = mealInsertDTO.getSetmealDishes();
        if(CollectionUtils.isEmpty(dishes)){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        dishes.forEach(dish->dish.setSetmealId(id));
        Db.saveBatch(dishes);
    }
}
