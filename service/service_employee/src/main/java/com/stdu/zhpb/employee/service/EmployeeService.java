package com.stdu.zhpb.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.vo.EmployeeQueryVo;

import java.util.Map;

public interface EmployeeService extends IService<Employee> {

    public Map<String,Object> findPageEmployee(Page<Employee> pageParam, EmployeeQueryVo employeeQueryVo);

    Result addEmployee(Employee employee);

    Result deleteEmployeeById(String id);

    Result updateEmployee(Employee employee);
}
