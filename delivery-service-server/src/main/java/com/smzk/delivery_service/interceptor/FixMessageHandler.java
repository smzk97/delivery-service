package com.smzk.delivery_service.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class FixMessageHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("自动填充，{}",metaObject);

        LocalDateTime time = LocalDateTime.now();
        Integer id = ThreadLocalUtils.getEmployee().getId();

        this.strictInsertFill(metaObject,"createUser",Integer.class,id);
        this.strictInsertFill(metaObject,"updateUser",Integer.class,id);
        this.strictInsertFill(metaObject,"createTime",LocalDateTime.class,time);
        this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class,time);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("自动填充，{}",metaObject);

        LocalDateTime time = LocalDateTime.now();
        Integer id = ThreadLocalUtils.getEmployee().getId();

        this.strictUpdateFill(metaObject,"updateUser", Integer.class,id);
        this.strictUpdateFill(metaObject,"updateTime", LocalDateTime.class,time);

    }
}
