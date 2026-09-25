package com.smzk.delivery_service.service.admin;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.*;
import com.smzk.delivery_service.entity.admin.Employee;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.vo.admin.PageResultVO;

public interface EmployeeService extends IService<Employee> {
    Result employeeLogin(EmployeeLoginDTO employeeLoginDTO);
    void employeeInsert(EmployeeInsertDTO employeeInsertDTO);
    PageResultVO employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO);
    void employeeConvertStatus(Integer status,Integer id);
    Employee employeeQueryById(Integer id);
    void employeeUpdate(EmployeeInsertDTO employeeInsertDTO);
    void employeeEditPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);
}
