package com.smzk.delivery_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateDTO {
    private Integer id;
    private String name;
    private Integer sort;
    private Integer type;
}
