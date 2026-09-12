package com.smzk.deliveryserviceserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smzk.deliveryservicecommon.dto.EmployeeLoginDTO;
import com.smzk.deliveryservicecommon.entity.Employee;
import com.smzk.deliveryservicecommon.entity.Result;
import com.smzk.deliveryservicecommon.enums.ErrorCode;
import com.smzk.deliveryservicecommon.vo.EmployeeLoginVO;
import com.smzk.deliveryserviceserver.mapper.EmployeeMapper;
import com.smzk.deliveryserviceserver.service.EmployeeService;
import com.smzk.deliveryservicesupport.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import static com.smzk.deliveryservicecommon.enums.AccountStatus.NORMAL;
import static com.smzk.deliveryservicecommon.utils.JwtUtil.generateToken;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Result EmployeeLogin(EmployeeLoginDTO employeeLoginDTO) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .select(Employee::getId,Employee::getName,Employee::getStatus)
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
        Map<String,Object> claims = Map.of("name",employeeData.getName(),"id",employeeData.getId());
        long time = 60 * 60 * 12 * 1000;
        String token = generateToken(employeeData.getUserName(),claims,time);
        employeeLoginVO.setToken(token);
        return Result.Success(employeeLoginVO);
    }
}
