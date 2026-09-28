package com.smzk.delivery_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartInsertDTO {
    private Integer dishId;
    private Integer setmealId;
    private String dishFlavor;
}
