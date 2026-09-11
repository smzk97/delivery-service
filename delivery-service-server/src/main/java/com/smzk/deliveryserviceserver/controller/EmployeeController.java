package com.smzk.deliveryserviceserver.controller;

import com.smzk.deliveryservicecommon.dto.EmployeeLoginDTO;
import com.smzk.deliveryservicecommon.entity.Result;
import com.smzk.deliveryserviceserver.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    public Result employeeLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        return employeeService.EmployeeLogin(employeeLoginDTO);
    }
}
