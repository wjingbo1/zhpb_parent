package com.stdu.zhpb.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stdu.zhpb.model.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
