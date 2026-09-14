package com.smzk.delivery_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smzk.delivery_service.dto.EmployeeQueryPageDTO;
import com.smzk.delivery_service.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO);
}
