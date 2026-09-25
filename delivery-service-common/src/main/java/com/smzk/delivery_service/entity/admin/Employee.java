package com.smzk.delivery_service.entity.admin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.UPDATE)
    private Integer createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;
}
