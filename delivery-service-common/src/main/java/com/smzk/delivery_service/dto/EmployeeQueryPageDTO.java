package com.smzk.delivery_service.dto;

import com.smzk.delivery_service.entity.admin.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQueryPageDTO extends Page {
    private String name;
}
