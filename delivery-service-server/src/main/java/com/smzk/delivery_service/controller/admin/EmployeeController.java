package com.smzk.delivery_service.controller.admin;

import com.smzk.delivery_service.dto.EmployeeEditPasswordDTO;
import com.smzk.delivery_service.dto.EmployeeInsertDTO;
import com.smzk.delivery_service.dto.EmployeeLoginDTO;
import com.smzk.delivery_service.dto.EmployeeQueryPageDTO;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.service.EmployeeService;
import com.smzk.delivery_service.vo.PageResultVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return employeeService.employeeLogin(employeeLoginDTO);
    }

    @PostMapping
    public Result employeeInsert(@RequestBody EmployeeInsertDTO employeeInsertDTO){
        employeeService.employeeInsert(employeeInsertDTO);
        return Result.Success();
    }

//    @GetMapping("/page")
//    public Result employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO){
//        PageResultVO pageResultDTO = employeeService.employeeQueryPage(employeeQueryPageDTO);
//        return Result.Success(pageResultDTO);
//    }

    @GetMapping("/page")
    public Result employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO){
        PageResultVO pageResultVO = employeeService.employeeQueryPage(employeeQueryPageDTO);
        return Result.Success(pageResultVO);
    }

    @PostMapping("/status/{status}")
    public Result employeeConvertStatus(@PathVariable Integer status,Integer id){
        employeeService.employeeConvertStatus(status,id);
        return Result.Success();
    }

    @GetMapping("/{id}")
    public Result employeeQueryById(@PathVariable Integer id){
        Employee employee = employeeService.employeeQueryById(id);
        return Result.Success(employee);
    }

    @PutMapping
    public Result employeeUpdate(@RequestBody EmployeeInsertDTO employeeInsertDTO)  {
        employeeService.employeeUpdate(employeeInsertDTO);
        return Result.Success();
    }

    @PutMapping("/editPassword")
    public Result employeeEditPassword(@RequestBody EmployeeEditPasswordDTO employeeEditPasswordDTO){
        employeeService.employeeEditPassword(employeeEditPasswordDTO);
        return Result.Success();
    }

}
