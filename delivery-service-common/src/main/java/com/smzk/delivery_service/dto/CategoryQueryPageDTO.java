package com.smzk.delivery_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryQueryPageDTO {
    private String name;
    private Integer page;
    private Integer pageSize;
    private Integer type;
}
