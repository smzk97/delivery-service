package com.smzk.delivery_service.service.Impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.DishInsertDTO;
import com.smzk.delivery_service.dto.DishQueryPageDTO;
import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.entity.admin.Dish;
import com.smzk.delivery_service.entity.admin.DishFlavor;
import com.smzk.delivery_service.entity.admin.SetmealDish;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.mapper.admin.DishMapper;
import com.smzk.delivery_service.service.admin.DishService;
import com.smzk.delivery_service.vo.admin.DishQueryByIdVO;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<BaseMapper<Dish>,Dish> implements DishService {

    private DishMapper dishMapper;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DishServiceImpl(DishMapper dishMapper,StringRedisTemplate stringRedisTemplate){
        this.dishMapper = dishMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dishInsert(DishInsertDTO dishInsertDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishInsertDTO,dish);
        this.save(dish);

        Integer categoryID = dishInsertDTO.getCategoryId();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        stringRedisTemplate.delete("dish_" + categoryID);
                    }
                }
        );

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
        Set<Integer> set = dishes.stream().map(Dish::getCategoryId).collect(Collectors.toSet());
        List<String> setLists = set.stream().map(s->{return "dish_" + s;}).toList();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        stringRedisTemplate.delete(setLists);
                    }
                }
        );
    }

    @Override
    public DishQueryByIdVO dishQueryById(Integer id) {
        Dish one = this.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId, id));
        if(one == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"查询为空");
        }
        List<DishFlavor> list = Db.lambdaQuery(DishFlavor.class).eq(DishFlavor::getDishId,id).list();
        Category byId = Db.getById(one.getCategoryId(), Category.class);
        String categoryName = byId != null ? byId.getName() : null;
        DishQueryByIdVO dishQueryByIdVO = new DishQueryByIdVO();
        dishQueryByIdVO.setFlavors(list);
        dishQueryByIdVO.setCategoryName(categoryName);
        BeanUtils.copyProperties(one,dishQueryByIdVO);
        return dishQueryByIdVO;
    }

    @Override
    public List<Dish> dishQueryByCategoryId(Integer categoryId) {
        return this.list(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, categoryId));
    }

    @Override
    public PageResultVO dishQueryPage(DishQueryPageDTO dishQueryPageDTO) {
        Integer categoryId = dishQueryPageDTO.getCategoryId();
        String name = dishQueryPageDTO.getName();
        Integer status = dishQueryPageDTO.getStatus();
        IPage<Dish> iPage = new Page<>(dishQueryPageDTO.getPage(),dishQueryPageDTO.getPageSize());
        IPage<Dish> iPages = this.page(iPage,new LambdaQueryWrapper<Dish>().select(Dish::getId)
                .eq(categoryId != null, Dish::getCategoryId, dishQueryPageDTO.getCategoryId())
                .eq(status != null, Dish::getStatus, status)
                .like(StringUtils.hasText(name), Dish::getName,name));
        List<Dish> data = iPages.getRecords();
        if(CollectionUtils.isEmpty(data)){
            return new PageResultVO(iPages.getTotal(),data);
        }
        List<Integer> ids = data.stream().map(Dish::getId).toList();
        List<Dish> dishes = this.listByIds(ids);
        return new PageResultVO(iPages.getTotal(),dishes);
    }

    @Override
    public void dishConvertStatus(Integer status, Integer id) {
        Dish dish = this.getById(id);
        if(dish == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"该菜品不存在");
        }
        if(!dish.getStatus().equals(status)){
            Dish update = new Dish();
            update.setId(id);
            update.setStatus(status);
            this.updateById(update);
        }
        Integer categoryId = dish.getCategoryId();
        String key = "dish_" + categoryId;
        stringRedisTemplate.delete(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dishUpdate(DishInsertDTO dishUpdateDTO) {
        if(dishUpdateDTO.getId() == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishUpdateDTO,dish);
        this.updateById(dish);

        List<DishFlavor> flavors = dishUpdateDTO.getFlavors();
        Integer id = dish.getId();
        Db.remove(Wrappers.lambdaQuery(DishFlavor.class).eq(DishFlavor::getDishId,id));
        flavors.forEach(flavor->flavor.setDishId(id));
        Db.saveBatch(flavors);

        String key = "dish_" + dishUpdateDTO.getCategoryId();
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        stringRedisTemplate.delete(key);
                    }
                }
        );
    }
}
