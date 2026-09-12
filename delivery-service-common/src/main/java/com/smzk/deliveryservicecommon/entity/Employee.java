package com.smzk.deliveryservicecommon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

// 用户对象实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Date createTime;
    private Date updateTime;
    private Integer createUser;
    private Integer updateUser;
}
