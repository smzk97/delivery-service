package com.smzk.delivery_service.aop;


import com.smzk.delivery_service.annotation.FixMessage;
import com.smzk.delivery_service.enums.OperateMethod;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * AOP切面，用于自动设置createTime、createUser、updateTime、updateUser
 * 由于service传入的是DTO，改用mp的 自动填充字段 功能
 */
@Aspect
@Component
public class FixMessageAspect {

    @Before("@annotation(com.smzk.delivery_service.annotation.FixMessage)")
    public void Operate(@NonNull JoinPoint jp){
        MethodSignature signature = (MethodSignature) jp.getSignature();
        FixMessage annotation = signature.getMethod().getAnnotation(FixMessage.class);
        OperateMethod object = annotation.value();

        Object[] args = jp.getArgs();
        Object arg = args[0];

        LocalDateTime time = LocalDateTime.now();
        Integer id = ThreadLocalUtils.getEmployee().getId();

        if(object == null){
            throw new RuntimeException("空指针异常");
        }
        if(object.equals(OperateMethod.INSERT)){
            try{
                Method setCreatTime = arg.getClass().getMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = arg.getClass().getMethod("setCreateUser", Integer.class);
                Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Integer.class);

                setCreatTime.invoke(arg,time);
                setCreateUser.invoke(arg,id);
                setUpdateTime.invoke(arg,time);
                setUpdateUser.invoke(arg,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if(object.equals(OperateMethod.UPDATE)){
            try{
                Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Integer.class);

                setUpdateTime.invoke(arg,time);
                setUpdateUser.invoke(arg,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
