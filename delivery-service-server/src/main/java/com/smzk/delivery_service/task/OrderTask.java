package com.smzk.delivery_service.task;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.smzk.delivery_service.entity.user.Orders;
import com.smzk.delivery_service.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderTask {

    @Scheduled(cron = "0/5 * * * * *")
    public void orderTimeOut(){
        log.info("处理超时付款订单");
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders = Db.lambdaQuery(Orders.class).eq(Orders::getStatus, OrderStatus.PENDING_PAYMENT).lt(Orders::getOrderTime, time).list();
        if(!CollectionUtils.isEmpty(orders)){
            for(Orders order:orders){
                order.setStatus(OrderStatus.CANCELLED.getCode());
                order.setCancelReason("订单付款超时，自动取消");
                order.setCancelTime(time);
            }
            Db.updateBatchById(orders);
        }
    }

    @Scheduled(cron = "1/6 * * * * *")
    public void orderFinished(){
        LocalDateTime time = LocalDateTime.now().plusMinutes(-1);
        List<Orders> orders = Db.lambdaQuery(Orders.class).eq(Orders::getStatus, OrderStatus.DELIVERING).lt(Orders::getDeliveryTime, time).list();
        if(!CollectionUtils.isEmpty(orders)){
            for(Orders order:orders){
                order.setStatus(OrderStatus.COMPLETED.getCode());
            }
            Db.updateBatchById(orders);
        }
    }
}
