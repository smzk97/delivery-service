package com.smzk.deliveryserviceserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smzk.deliveryservicecommon.dto.EmployeeLoginDTO;
import com.smzk.deliveryservicecommon.entity.Employee;
import com.smzk.deliveryservicecommon.entity.Result;
import com.smzk.deliveryserviceserver.mapper.EmployeeMapper;
import com.smzk.deliveryserviceserver.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Result EmployeeLogin(EmployeeLoginDTO employeeLoginDTO) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .select(Employee::getId,Employee::getName,Employee::getStatus)
                .allEq(Map.of(Employee::getUserName,employeeLoginDTO.getUserName(),Employee::getPassWord, employeeLoginDTO.getPassWord()));
    }
}
