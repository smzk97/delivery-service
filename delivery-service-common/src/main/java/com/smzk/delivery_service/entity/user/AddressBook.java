package com.smzk.delivery_service.entity.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook {
    private Integer id;
    private Integer userId;
    @NotNull(message = "收货人不能为空")
    private String consignee;
    private String sex;
    @NotNull(message = "手机号不能为空")
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    @NotNull(message = "地址不能为空")
    private String detail;
    private String label;
    private Integer isDefault;
}
