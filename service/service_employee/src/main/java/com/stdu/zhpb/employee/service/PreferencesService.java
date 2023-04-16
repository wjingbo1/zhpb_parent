package com.stdu.zhpb.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.model.Preferences;
import com.stdu.zhpb.vo.PreferenceVo;

import java.util.Map;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
public interface PreferencesService extends IService<Preferences> {
    Map<String, Object> findPagePreference(Page<Preferences> pageParam, PreferenceVo preferenceVo);
}
