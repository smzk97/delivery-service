package com.smzk.delivery_service.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smzk.delivery_service.entity.user.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOrder extends BaseMapper<Orders> {
}
