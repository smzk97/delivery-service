package com.smzk.deliveryservicecommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginDTO {
    private String userName;
    private String passWord;
}
