package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.user.OrderQueryHistory;
import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.admin.Page;
import com.smzk.delivery_service.entity.user.Orders;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import com.smzk.delivery_service.vo.user.DetailOrderVO;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;

public interface UserOrderService extends IService<Orders> {
    OrderSubmitVO userOrderSumbit(OrderSubmitDTO orderSubmitDTO);

    PageResultVO userQueryHistoryOrders(OrderQueryHistory orderQueryHistory);

    DetailOrderVO userOrderDetailQuery(Integer id);
}
