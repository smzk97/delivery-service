package com.smzk.delivery_service.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.*;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.vo.PageResultVO;
import org.springframework.web.bind.annotation.PathVariable;

public interface EmployeeService extends IService<Employee> {
    Result employeeLogin(EmployeeLoginDTO employeeLoginDTO);
    void employeeInsert(EmployeeInsertDTO employeeInsertDTO);
    PageResultVO employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO);
    void employeeConvertStatus(Integer status,Integer id);
    Employee employeeQueryById(Integer id);
    void employeeUpdate(EmployeeInsertDTO employeeInsertDTO);
    void employeeEditPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);
}
