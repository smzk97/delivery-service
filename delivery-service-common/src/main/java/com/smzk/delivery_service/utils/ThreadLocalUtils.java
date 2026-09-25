package com.smzk.delivery_service.utils;

import com.smzk.delivery_service.entity.admin.EmployeeThreadLocal;

public class ThreadLocalUtils {
    private static final ThreadLocal<EmployeeThreadLocal> THREAD_LOCAL = new ThreadLocal<>();

    public static EmployeeThreadLocal getEmployee(){
        return THREAD_LOCAL.get();
    }

    public static void setEmployee(EmployeeThreadLocal employeeThreadLocal){
        THREAD_LOCAL.set(employeeThreadLocal);
    }

    public static void removeEmployee(){
        THREAD_LOCAL.remove();
    }
}
