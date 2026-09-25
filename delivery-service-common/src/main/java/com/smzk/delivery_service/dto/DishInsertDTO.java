package com.smzk.delivery_service.dto;

import com.smzk.delivery_service.entity.admin.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishInsertDTO {
    private Integer id;
    private String description;
    private List<DishFlavor> flavors;
    private Integer categoryId;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer status;
}
