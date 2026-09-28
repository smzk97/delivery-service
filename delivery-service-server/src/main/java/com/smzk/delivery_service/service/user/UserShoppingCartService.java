package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.user.ShoppingCartInsertDTO;
import com.smzk.delivery_service.entity.user.ShoppingCart;

import java.util.List;

public interface UserShoppingCartService extends IService<ShoppingCart> {
    void shoppingCartInsert(ShoppingCartInsertDTO shoppingCartInsertDTO);

    List<ShoppingCart> shoppingCartQuery();

    void shoppingCartClean();

    void shoppingCartSub(ShoppingCartInsertDTO shoppingCartInsertDTO);
}
