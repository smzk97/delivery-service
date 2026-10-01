package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.UserOrderService;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    private UserOrderService userOrderService;
    @Autowired
    UserOrderController(UserOrderService userOrderService){
        this.userOrderService = userOrderService;
    }

    @PostMapping("/submit")
    public Result userOrderSubmit(@RequestBody OrderSubmitDTO orderSubmitDTO){
        OrderSubmitVO orderSubmitVO = userOrderService.userOrderSumbit(orderSubmitDTO);
        return Result.Success(orderSubmitVO);
    }
}
