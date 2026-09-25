package com.smzk.delivery_service.dto;

import com.smzk.delivery_service.entity.admin.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQueryPageDTO extends Page{
    private String name;
    private Integer type;
}
