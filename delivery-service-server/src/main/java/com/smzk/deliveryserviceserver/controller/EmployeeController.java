package com.smzk.deliveryserviceserver.controller;

import com.smzk.deliveryservicecommon.dto.EmployeeLoginDTO;
import com.smzk.deliveryservicecommon.entity.Result;
import com.smzk.deliveryserviceserver.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Tag(name = "用户管理")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public Result employeeLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        return employeeService.EmployeeLogin(employeeLoginDTO);
    }
}
