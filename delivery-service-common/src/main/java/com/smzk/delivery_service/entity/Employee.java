package com.smzk.delivery_service.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 用户对象实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("employee")
public class Employee {
    private Integer id;
    private String name;
    private String userName;
    private String passWord;
    private String phone;
    private Integer sex;
    private String identifyNumber;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer createUser;
    private Integer updateUser;
}
