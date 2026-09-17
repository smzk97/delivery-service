package com.smzk.delivery_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.*;
import com.smzk.delivery_service.entity.Category;
import com.smzk.delivery_service.entity.Employee;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import com.smzk.delivery_service.vo.EmployeeLoginVO;
import com.smzk.delivery_service.mapper.EmployeeMapper;
import com.smzk.delivery_service.service.EmployeeService;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.vo.PageResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.smzk.delivery_service.enums.AccountStatus.NORMAL;
import static com.smzk.delivery_service.utils.JwtUtils.generateToken;

@Service
public class EmployeeServiceImpl extends ServiceImpl<BaseMapper<Employee>,Employee> implements EmployeeService{

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Result employeeLogin(EmployeeLoginDTO employeeLoginDTO) {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .select(Employee::getId,Employee::getUserName,Employee::getName,Employee::getStatus)
                .allEq(Map.of(Employee::getUserName,employeeLoginDTO.getUserName(),Employee::getPassWord, employeeLoginDTO.getPassWord()));
        Employee employeeData = employeeMapper.selectOne(lambdaQueryWrapper);
        if(employeeData == null){
            throw new BusinessException(ErrorCode.UNAUTHORIZED,"用户名或密码错误");
        }
        if(!employeeData.getStatus().equals(NORMAL.getNum())){
            throw new BusinessException(ErrorCode.LOCK);
        }
        EmployeeLoginVO employeeLoginVO = new EmployeeLoginVO();
        BeanUtils.copyProperties(employeeData,employeeLoginVO);
        Map<String,Object> claims = Map.of("name",employeeData.getName(),"id",employeeData.getId(),"userName",employeeData.getUserName(),"status",employeeData.getStatus());
        long time = 30L * 60 * 60 * 24 * 1000;
        String token = generateToken(employeeData.getUserName(),claims,time);
        employeeLoginVO.setToken(token);
        return Result.Success(employeeLoginVO);
    }

    @Override
    public void employeeInsert(EmployeeInsertDTO employeeInsertDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeInsertDTO,employee);
        this.save(employee);
    }

//    @Override
//    public PageResultVO employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO) {
//        PageHelper.startPage(employeeQueryPageDTO.getPage(),employeeQueryPageDTO.getPageSize());
//        List<Employee> employees = employeeMapper.employeeQueryPage(employeeQueryPageDTO);
//        Page<Employee> employeeDatas = (Page<Employee>) employees;
//        return new PageResultVO(employeeDatas.getTotal(),employeeDatas.getResult());
//    }

    @Override
    public PageResultVO employeeQueryPage(EmployeeQueryPageDTO employeeQueryPageDTO) {
        IPage<Employee> employee = new Page<>(employeeQueryPageDTO.getPage(),employeeQueryPageDTO.getPageSize());
        String name = employeeQueryPageDTO.getName();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .like(name != null && !name.isEmpty(),Employee::getName,'%'+name+'%');
        IPage<Employee> employees = this.page(employee,lambdaQueryWrapper);
        List<Employee> employeeList = employees.getRecords();

        if(CollectionUtils.isEmpty(employeeList)){
            return new PageResultVO(employees.getTotal(),employeeList);
        }

        List<Integer> idLists = employeeList.stream().map(Employee::getId).toList();
        List<Employee> employeeLists = this.listByIds(idLists);

        return new PageResultVO(employees.getTotal(),employeeLists);
    }

    @Override
    public void employeeConvertStatus(Integer status, Integer id) {
        LambdaUpdateWrapper<Employee> lambdaQueryWrapper = new LambdaUpdateWrapper<Employee>()
                .set(Employee::getStatus,status)
                .set(Employee::getUpdateTime,LocalDateTime.now())
                .eq(Employee::getId,id);
        this.update(lambdaQueryWrapper);
    }

    @Override
    public Employee employeeQueryById(Integer id) {
        return this.getById(id);
    }

    @Override
    public void employeeUpdate(EmployeeInsertDTO employeeInsertDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeInsertDTO,employee);
        this.update(employee,new LambdaUpdateWrapper<Employee>().eq(Employee::getId,employeeInsertDTO.getId()));
    }

    @Override
    public void employeeEditPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        String recentPassword = this.employeeQueryById(employeeEditPasswordDTO.getEmpId()).getPassWord();
        if(!recentPassword.equals(employeeEditPasswordDTO.getOldPassword())){
            throw new BusinessException(ErrorCode.UNAUTHORIZED,"旧密码错误");
        }
        if(recentPassword.equals(employeeEditPasswordDTO.getNewPassword())){
            throw new BusinessException(ErrorCode.UNAUTHORIZED,"旧密码与新密码一致");
        }
        Employee employee = Employee.builder()
                .passWord(employeeEditPasswordDTO.getNewPassword())
                .id(employeeEditPasswordDTO.getEmpId())
                .build();
        this.update(employee,new LambdaUpdateWrapper<Employee>().eq(Employee::getId,employeeEditPasswordDTO.getEmpId()));
    }

}
