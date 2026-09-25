package com.smzk.delivery_service.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginVO {
     private Integer id;
     private String name;
     private Integer status;
     private String token;
}
