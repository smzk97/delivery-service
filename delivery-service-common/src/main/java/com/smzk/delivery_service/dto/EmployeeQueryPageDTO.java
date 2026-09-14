package com.smzk.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQueryPageDTO {
    private String name;
    private Integer page;
    private Integer pageSize;
}
