package com.smzk.deliveryservicecommon.vo;

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
