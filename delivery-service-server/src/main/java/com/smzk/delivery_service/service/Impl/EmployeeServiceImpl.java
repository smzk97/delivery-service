package com.smzk.delivery_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.EmployeeLoginDTO;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.vo.EmployeeLoginVO;
import com.smzk.delivery_service.mapper.EmployeeMapper;
import com.smzk.delivery_service.service.EmployeeService;
import com.smzk.delivery_service.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import static com.smzk.delivery_service.enums.AccountStatus.NORMAL;
import static com.smzk.delivery_service.utils.JwtUtil.generateToken;

@Service
public class EmployeeServiceImpl extends ServiceImpl<BaseMapper<Employee>,Employee> implements EmployeeService{

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Result EmployeeLogin(EmployeeLoginDTO employeeLoginDTO) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .select(Employee::getId,Employee::getUserName,Employee::getName,Employee::getStatus)
                .allEq(Map.of(Employee::getUserName,employeeLoginDTO.getUserName(),Employee::getPassWord, employeeLoginDTO.getPassWord()));
        Employee employeeData = employeeMapper.selectOne(lambdaQueryWrapper);
        if(employeeData == null){
            throw new BusinessException(ErrorCode.UNAUTHORIZED,"用户或密码错误");
        }
        if(!employeeData.getStatus().equals(NORMAL.getNum())){
            throw new BusinessException(ErrorCode.LOCK);
        }
        EmployeeLoginVO employeeLoginVO = new EmployeeLoginVO();
        BeanUtils.copyProperties(employeeData,employeeLoginVO);
        Map<String,Object> claims = Map.of("name",employeeData.getName(),"id",employeeData.getId(),"userName",employeeData.getUserName(),"status",employeeData.getStatus());
        long time = 60 * 60 * 12 * 1000;
        String token = generateToken(employeeData.getUserName(),claims,time);
        employeeLoginVO.setToken(token);
        return Result.Success(employeeLoginVO);
    }
}
