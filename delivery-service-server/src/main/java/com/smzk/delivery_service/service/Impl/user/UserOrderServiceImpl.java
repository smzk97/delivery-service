package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.BusinessException;
import com.smzk.delivery_service.dto.user.OrderQueryHistory;
import com.smzk.delivery_service.dto.user.OrderSubmitDTO;
import com.smzk.delivery_service.entity.user.*;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.enums.OrderStatus;
import com.smzk.delivery_service.enums.PaymentStatus;
import com.smzk.delivery_service.service.user.UserOrderService;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import com.smzk.delivery_service.vo.user.DetailOrderVO;
import com.smzk.delivery_service.vo.user.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public PageResultVO userQueryHistoryOrders(OrderQueryHistory orderQueryHistory) {
        IPage<OrderDetail> iPage = new Page<>(orderQueryHistory.getPage(),orderQueryHistory.getPageSize());
        IPage<OrderDetail> orderDetailIPage = Db.lambdaQuery(OrderDetail.class)
                .select(OrderDetail::getId)
                .eq(orderQueryHistory.getStatus() != null,OrderDetail::getStatus, orderQueryHistory.getStatus())
                .page(iPage);
        List<OrderDetail> orderDetailList = orderDetailIPage.getRecords();
        Long total = orderDetailIPage.getTotal();
        if(CollectionUtils.isEmpty(orderDetailList)){
            return new PageResultVO(total, Collections.emptyList());
        }
        List<Integer> ids = orderDetailList.stream().map(OrderDetail::getId).toList();
        List<OrderDetail> lists = Db.listByIds(ids,OrderDetail.class);
        Map<Integer, List<OrderDetail>> collect = lists.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));
        List<List<OrderDetail>> valuesLists = collect.values().stream().toList();
        return new PageResultVO(total,valuesLists);
    }

    @Override
    public DetailOrderVO userOrderDetailQuery(Integer id) {
        Orders byId = this.getById(id);
        if(byId == null){
            throw new BusinessException(ErrorCode.NOT_FOUND,"该订单不存在");
        }
        DetailOrderVO detailOrderVO = new DetailOrderVO();
        BeanUtils.copyProperties(byId,detailOrderVO);
        List<OrderDetail> ts = Db.list(Wrappers.lambdaQuery(OrderDetail.class).eq(OrderDetail::getOrderId,id));
        detailOrderVO.setOrderDetail(ts);
        return detailOrderVO;
    }
}
