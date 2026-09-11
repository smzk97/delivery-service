package com.smzk.deliveryserviceserver.service;

import com.smzk.deliveryservicecommon.dto.EmployeeLoginDTO;
import com.smzk.deliveryservicecommon.entity.Result;

public interface EmployeeService {
    public Result EmployeeLogin(EmployeeLoginDTO employeeLoginDTO);
}
