package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.user.UserService;
import com.smzk.delivery_service.vo.user.LoginVO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public Result UserLogin(String code) throws IOException, InterruptedException {
        LoginVO loginVO = userService.userLogin(code);
        return Result.Success(loginVO);
    }
}
