package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.dto.user.ShoppingCartInsertDTO;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.entity.user.ShoppingCart;
import com.smzk.delivery_service.service.user.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
public class UserShoppingCartController {

    private UserShoppingCartService userShoppingCartService;

    @Autowired
    UserShoppingCartController(UserShoppingCartService userShoppingCartService){
        this.userShoppingCartService = userShoppingCartService;
    }

    @PostMapping("/add")
    public Result shoppingCartInsert(@RequestBody ShoppingCartInsertDTO shoppingCartInsertDTO){
        userShoppingCartService.shoppingCartInsert(shoppingCartInsertDTO);
        return Result.Success();
    }

    @PutMapping("/sub")
    public Result shoppingCartSub(@RequestBody ShoppingCartInsertDTO shoppingCartInsertDTO){
        userShoppingCartService.shoppingCartSub(shoppingCartInsertDTO);
        return Result.Success();
    }

    @GetMapping("/list")
    public Result shoppingCartQuery(){
        List<ShoppingCart> carts = userShoppingCartService.shoppingCartQuery();
        return Result.Success(carts);
    }

    @DeleteMapping("/clean")
    public Result shoppingCartClean(){
        userShoppingCartService.shoppingCartClean();
        return Result.Success();
    }
}
