package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.dto.user.OrderQueryHistory;
import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.admin.Page;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.UserOrderService;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import com.smzk.delivery_service.vo.user.DetailOrderVO;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/historyOrders")
    public Result userOrderQueryHistoryOrders(OrderQueryHistory orderQueryHistory){
        PageResultVO pages = userOrderService.userQueryHistoryOrders(orderQueryHistory);
        return Result.Success(pages);
    }

    @GetMapping("/orderDetail/{id}")
    public Result userOrderQueryHistoryDetailOrders(@PathVariable Integer id){
        DetailOrderVO detailOrderVO = userOrderService.userOrderDetailQuery(id);
        return Result.Success(detailOrderVO);
    }
}
