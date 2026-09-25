package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop")
public class UserShopStatusController {

    private final String STATUS_NAME = "SHOP_STATUS";
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public UserShopStatusController(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @RequestMapping("/status")
    public Result GetStatus(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String s = stringStringValueOperations.get(STATUS_NAME);
        return Result.Success(s);
    }

}
