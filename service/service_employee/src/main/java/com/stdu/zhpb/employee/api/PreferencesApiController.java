package com.stdu.zhpb.employee.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stdu.zhpb.employee.mapper.PreferenceMapper;
import com.stdu.zhpb.model.Preferences;
import com.stdu.zhpb.model.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */

@Api(tags = "偏好信息")
@RestController
@RequestMapping("/api/preference")
public class PreferencesApiController {

    @Autowired
    private PreferenceMapper preferenceMapper;

    @ApiOperation("获取店铺信息")
    @GetMapping("inner/getAll")
    public List<Preferences> getAll(){
        List<Preferences> preferences = preferenceMapper.selectList(new QueryWrapper<Preferences>());
        return preferences;
    }
}
