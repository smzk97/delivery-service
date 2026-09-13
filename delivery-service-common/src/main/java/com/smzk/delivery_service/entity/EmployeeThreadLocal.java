package com.smzk.delivery_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeThreadLocal {
    private Integer id;
    private String name;
    private String userName;
    private Integer status;
}
