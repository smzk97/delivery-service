package com.smzk.delivery_service.vo;

import com.smzk.delivery_service.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResultVO {
    private long total;
    private List<?> result;
}
