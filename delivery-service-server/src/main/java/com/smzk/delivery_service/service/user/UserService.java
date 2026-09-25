package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.entity.user.User;
import com.smzk.delivery_service.vo.user.LoginVO;

import java.io.IOException;

public interface UserService extends IService<User> {
    LoginVO userLogin(String code) throws IOException, InterruptedException;
}
