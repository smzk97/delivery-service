package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.user.Orders;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;

public interface UserOrderService extends IService<Orders> {
    OrderSubmitVO userOrderSumbit(OrderSubmitDTO orderSubmitDTO);
}
