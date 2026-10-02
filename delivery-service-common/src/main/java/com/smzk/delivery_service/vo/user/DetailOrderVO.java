package com.smzk.delivery_service.vo.user;

import com.smzk.delivery_service.entity.user.OrderDetail;
import com.smzk.delivery_service.entity.user.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderVO extends Orders {
    private List<OrderDetail> orderDetail;
}
