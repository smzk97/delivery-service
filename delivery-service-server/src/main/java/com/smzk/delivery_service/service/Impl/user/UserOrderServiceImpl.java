package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.BusinessException;
import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.user.*;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.enums.OrderStatus;
import com.smzk.delivery_service.enums.PaymentStatus;
import com.smzk.delivery_service.service.user.UserOrderService;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserOrderServiceImpl extends ServiceImpl<BaseMapper<Orders>,Orders> implements UserOrderService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO userOrderSumbit(OrderSubmitDTO orderSubmitDTO) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user== null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"上下文丢失");
        }
        AddressBook byId = Db.getById(orderSubmitDTO.getAddressBookId(), AddressBook.class);
        if(byId == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"地址簿不存在");
        }
        List<ShoppingCart> list = Db.lambdaQuery(ShoppingCart.class).eq(ShoppingCart::getUserId, user.getId()).list();
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.NOT_FOUND,"购物车不存在");
        }
        String Number = String.valueOf(System.currentTimeMillis());
        LocalDateTime time = LocalDateTime.now();
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO,orders);
        orders.setNumber(Number);
        orders.setStatus(OrderStatus.PENDING_PAYMENT.getCode());
        orders.setUserId(user.getId());
        orders.setOrderTime(time);
        orders.setPayStatus(PaymentStatus.UNPAID.getCode());
        orders.setPhone(byId.getPhone());
        orders.setAddress(byId.getDetail());
        orders.setConsignee(byId.getConsignee());
        this.save(orders);
        List<OrderDetail> orderDetails = list.stream().map(l -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(l, orderDetail);
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).toList();
        Db.saveBatch(orderDetails);
        Db.lambdaUpdate(ShoppingCart.class).eq(ShoppingCart::getUserId,user.getId()).remove();
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderAmount(orderSubmitDTO.getAmount())
                .orderNumber(Number)
                .orderTime(time)
                .build();
    }
}
