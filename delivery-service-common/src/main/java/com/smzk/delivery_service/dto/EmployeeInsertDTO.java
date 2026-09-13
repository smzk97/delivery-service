package com.smzk.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInsertDTO {
    private Integer id;
    private String identifyNumber;
    private String name;
    private String phone;
    private Integer sex;
    private String userName;
}
