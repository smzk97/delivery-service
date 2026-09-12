package com.smzk.deliveryserviceserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smzk.deliveryservicecommon.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
