package com.smzk.delivery_service.utils;

import com.smzk.delivery_service.entity.admin.EmployeeThreadLocal;
import com.smzk.delivery_service.entity.user.UserThreadLocal;

public class ThreadLocalUtils {
    private static final ThreadLocal<EmployeeThreadLocal> EMPLOYEE_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<UserThreadLocal> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static EmployeeThreadLocal getEmployee(){
        return EMPLOYEE_THREAD_LOCAL.get();
    }

    public static void setEmployee(EmployeeThreadLocal employeeThreadLocal){
        EMPLOYEE_THREAD_LOCAL.set(employeeThreadLocal);
    }

    public static void removeEmployee(){
        EMPLOYEE_THREAD_LOCAL.remove();
    }

    public static UserThreadLocal getUser(){
        return USER_THREAD_LOCAL.get();
    }

    public static void setUser(UserThreadLocal userThreadLocal){
        USER_THREAD_LOCAL.set(userThreadLocal);
    }

    public static void removeUser(){
        USER_THREAD_LOCAL.remove();
    }
}
