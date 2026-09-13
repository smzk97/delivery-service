package com.smzk.delivery_service.controller;

import com.smzk.delivery_service.dto.EmployeeInsertDTO;
import com.smzk.delivery_service.dto.EmployeeLoginDTO;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import com.smzk.delivery_service.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/employee")
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

    @PostMapping
    public Result employeeInsert(@RequestBody EmployeeInsertDTO employeeInsertDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeInsertDTO,employee);
        employee.setCreateUser(ThreadLocalUtils.getEmployee().getId());
        employee.setUpdateUser(ThreadLocalUtils.getEmployee().getId());
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.save(employee);
        return Result.Success();
    }
}
