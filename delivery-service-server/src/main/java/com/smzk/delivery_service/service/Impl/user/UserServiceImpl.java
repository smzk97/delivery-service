package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.smzk.delivery_service.entity.user.LoginParam;
import com.smzk.delivery_service.entity.user.LoginResponse;
import com.smzk.delivery_service.entity.user.User;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.service.user.UserService;
import com.smzk.delivery_service.utils.HttpUtils;
import com.smzk.delivery_service.utils.JwtUtils;
import com.smzk.delivery_service.vo.user.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<BaseMapper<User>,User> implements UserService{

    private final String URI = "https://api.weixin.qq.com/sns/jscode2session";
    private ObjectMapper objectMapper = new ObjectMapper();
    private LoginParam loginParam;

    @Autowired
    UserServiceImpl(LoginParam loginParam){
        this.loginParam = loginParam;
    }

    @Override
    public LoginVO userLogin(String code) throws IOException, InterruptedException {
        if(code == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        LoginResponse loginResponse = userLoginProcedure(code);
        if(loginResponse.getErrcode() != null){
            throw new BusinessException(ErrorCode.PARAM_ERROR,loginResponse.getErrmsg());
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("secret_key",loginResponse.getSecretKey());
        claims.put("openid",loginResponse.getOpenid());
        String token = JwtUtils.generateToken(loginResponse.getOpenid(),claims,24L*60*60*30*1000);

        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getOpenid,loginResponse.getOpenid()));
        if(ObjectUtils.isEmpty(user)){
            User users = User.builder()
                    .openid(loginResponse.getOpenid())
                    .build();
            this.save(users);
        }

        LoginVO loginVO = LoginVO.builder()
                .openid(loginResponse.getOpenid())
                .token(token)
                .build();
        return loginVO;
    }

    public LoginResponse userLoginProcedure(String code) throws IOException, InterruptedException {
        this.loginParam.setJsCode(code);
        Map<String,String> map = objectMapper.convertValue(this.loginParam, new TypeReference<Map<String, String>>() {});
        String s = HttpUtils.get(URI, map,null);
        return objectMapper.readValue(s, LoginResponse.class);
    }
}
