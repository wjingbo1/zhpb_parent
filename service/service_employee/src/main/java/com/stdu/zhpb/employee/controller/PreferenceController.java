package com.stdu.zhpb.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stdu.zhpb.employee.service.PreferencesService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.model.Preferences;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.vo.EmployeeQueryVo;
import com.stdu.zhpb.vo.PreferenceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@Api(tags = "员工偏好管理接口")
@RestController
@RequestMapping("/admin/employee/preferences")
public class PreferenceController {

    @Autowired
    private PreferencesService preferencesService;

    @ApiOperation("偏好列表")
    @GetMapping("{page}/{limit}")
    public Result getPreferenceList(@PathVariable Long page,
                                  @PathVariable Long limit,
                                  PreferenceVo preferenceVo){
        Page<Preferences> pageParam = new Page<>(page,limit);
        Map<String,Object> map = preferencesService.findPagePreference(pageParam,preferenceVo);
        return Result.ok(map);
    }
}
