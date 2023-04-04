package com.stdu.zhpb.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stdu.zhpb.employee.service.EmployeeService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.vo.EmployeeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 员工 前端控制器
 *
 * @auther wjingbo
 * @since 2023-03-19
 */


@Api(tags = "员工管理接口")
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @ApiOperation("员工列表")
    @GetMapping("{page}/{limit}")
    public Result getEmployeeList(@PathVariable Long page,
                                  @PathVariable Long limit,
                                  EmployeeQueryVo employeeQueryVo){
        Page<Employee> pageParam = new Page<>(page,limit);
        Map<String,Object> map = employeeService.findPageEmployee(pageParam,employeeQueryVo);
        return Result.ok(map);
    }

    @ApiOperation("新增员工")
    @PostMapping("add")
    public Result addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("删除员工")
    @DeleteMapping
    public Result deleteEmployeeById(@RequestParam String id){
        return employeeService.deleteEmployeeById(id);
    }

    @ApiOperation("更新员工信息")
    @PutMapping
    public Result updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }
}
