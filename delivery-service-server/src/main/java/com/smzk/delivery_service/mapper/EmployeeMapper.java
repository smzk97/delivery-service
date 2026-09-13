package com.smzk.delivery_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smzk.delivery_service.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
