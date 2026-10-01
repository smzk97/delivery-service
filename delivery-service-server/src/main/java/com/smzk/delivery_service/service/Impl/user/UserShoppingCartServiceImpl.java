package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.user.ShoppingCartInsertDTO;
import com.smzk.delivery_service.entity.admin.Dish;
import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.entity.user.ShoppingCart;
import com.smzk.delivery_service.entity.user.UserThreadLocal;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.BusinessException;
import com.smzk.delivery_service.service.user.UserShoppingCartService;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserShoppingCartServiceImpl extends ServiceImpl<BaseMapper<ShoppingCart>,ShoppingCart> implements UserShoppingCartService {

    @Override
    public void shoppingCartInsert(ShoppingCartInsertDTO shoppingCartInsertDTO) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        Integer dishId = shoppingCartInsertDTO.getDishId();
        Integer setmealId = shoppingCartInsertDTO.getSetmealId();
        log.info("dishId:{},setmealId:{}",dishId,setmealId);
        if((dishId == null && setmealId == null) || (dishId != null && setmealId != null)){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        Integer user_id = user.getId();
        ShoppingCart byId = this.getOne(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId,user_id));
        if(byId == null){
            ShoppingCart cart = new ShoppingCart();
            if(dishId != null){
                Dish entity = Db.getById(dishId, Dish.class);
                if(entity == null){
                    throw new BusinessException(ErrorCode.PARAM_ERROR);
                }
                cart.setName(entity.getName());
                cart.setImage(entity.getImage());
                cart.setAmount(entity.getPrice());
                cart.setUserId(user_id);
                cart.setDishId(dishId);
                cart.setDishFlavor(shoppingCartInsertDTO.getDishFlavor());
                cart.setNumber(1);
                this.save(cart);
            }else{
                Setmeal entity = Db.getById(setmealId, Setmeal.class);
                if(entity == null){
                    throw new BusinessException(ErrorCode.PARAM_ERROR);
                }
                cart.setName(entity.getName());
                cart.setImage(entity.getImage());
                cart.setAmount(entity.getPrice());
                cart.setUserId(user_id);
                cart.setSetmealId(setmealId);
                cart.setDishFlavor(shoppingCartInsertDTO.getDishFlavor());
                cart.setNumber(1);
                this.save(cart);
            }
        }else{
            byId.setNumber(byId.getNumber() + 1);
            this.update(byId,new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId,user_id));
        }
    }

    @Override
    public List<ShoppingCart> shoppingCartQuery() {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        log.info("上下文：{}",user);
        Integer user_id = user.getId();
        List<ShoppingCart> list = this.list(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, user_id));
        return list;
    }

    @Override
    public void shoppingCartClean() {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        Integer user_id = user.getId();
        this.remove(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId,user_id));
    }

    @Override
    public void shoppingCartSub(ShoppingCartInsertDTO shoppingCartInsertDTO) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        Integer user_id = user.getId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, user_id)
                .eq(shoppingCartInsertDTO.getDishId() != null, ShoppingCart::getDishId, shoppingCartInsertDTO.getDishId())
                .eq(shoppingCartInsertDTO.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCartInsertDTO.getSetmealId());
        ShoppingCart one = this.getOne(wrapper);
        if(one == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"该菜品或套餐不存在");
        }
        Integer number = one.getNumber();
        if(number == 1){
            this.removeById(one.getId());
        }
        one.setNumber(one.getNumber() - 1);
        this.update(one,wrapper);
    }
}
