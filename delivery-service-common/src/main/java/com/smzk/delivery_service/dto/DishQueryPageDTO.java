package com.smzk.delivery_service.dto;

import com.smzk.delivery_service.entity.admin.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishQueryPageDTO extends Page {
    private Integer categoryId;
    private String name;
    private Integer status;
}
