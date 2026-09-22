package com.smzk.delivery_service.controller.admin;


import com.smzk.delivery_service.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/shop")
public class ShopStatusController {

    private final String STATUS_NAME = "SHOP_STATUS";
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public ShopStatusController(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PutMapping("/{status}")
    public Result SetStatus(@PathVariable Integer status){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set(STATUS_NAME,status.toString());
        return Result.Success();
    }

    @RequestMapping("/status")
    public Result GetStatus(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String s = stringStringValueOperations.get(STATUS_NAME);
        return Result.Success(s);
    }
}
