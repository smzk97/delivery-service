package com.smzk.delivery_service.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.EmployeeLoginDTO;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;

public interface EmployeeService extends IService<Employee> {
    public Result EmployeeLogin(EmployeeLoginDTO employeeLoginDTO);
}
