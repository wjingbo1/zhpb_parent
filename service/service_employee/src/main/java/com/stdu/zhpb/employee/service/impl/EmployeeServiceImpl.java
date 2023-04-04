package com.stdu.zhpb.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.zhpb.employee.mapper.EmployeeMapper;
import com.stdu.zhpb.employee.service.EmployeeService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.vo.EmployeeQueryVo;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author wjingbo
 * @since 2023-03-19
 */


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {


    @Override
    public Map<String, Object> findPageEmployee(Page<Employee> pageParam, EmployeeQueryVo employeeQueryVo) {

        String name = employeeQueryVo.getName();
        String email = employeeQueryVo.getEmail();
        String store = employeeQueryVo.getStore();
        String storeId = employeeQueryVo.getStoreId();

        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(storeId),Employee::getStoreId,storeId);
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(email),Employee::getEmail,email);
        lambdaQueryWrapper.like(!StringUtils.isEmpty(store),Employee::getStore,store);

        Page<Employee> employeePage = baseMapper.selectPage(pageParam, lambdaQueryWrapper);
        long totalCount = employeePage.getTotal();
        long totalPage = employeePage.getPages();

        List<Employee> list = employeePage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);

        return map;
    }

    @Override
    public Result addEmployee(Employee employee) {
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        int isSuccess = baseMapper.insert(employee);
        if (isSuccess > 0){
            return Result.ok(employee);
        }
        return Result.fail(null).message("插入失败");
    }

    @Override
    public Result deleteEmployeeById(String id) {
        int isSuccess = baseMapper.deleteById(id);
        if(isSuccess > 0){
            return Result.ok(null).message("删除成功");
        }
        return Result.fail(null).message("删除失败");
    }

    @Override
    public Result updateEmployee(Employee employee) {
        employee.setUpdateTime(new Date());
        int isSuccess = baseMapper.updateById(employee);
        if(isSuccess > 0){
            return Result.ok(null).message("更新成功");
        }
        return Result.fail(null).message("更新失败");
    }
}
