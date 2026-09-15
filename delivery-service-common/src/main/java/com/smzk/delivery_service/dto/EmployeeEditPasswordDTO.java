package com.smzk.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEditPasswordDTO {
    private Integer empId;
    private String newPassword;
    private String oldPassword;
}
