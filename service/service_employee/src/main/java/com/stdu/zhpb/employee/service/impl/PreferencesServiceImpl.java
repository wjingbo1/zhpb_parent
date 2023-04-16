package com.stdu.zhpb.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.zhpb.client.store.StoreInfoFeignClient;
import com.stdu.zhpb.employee.mapper.PreferenceMapper;
import com.stdu.zhpb.employee.service.PreferencesService;
import com.stdu.zhpb.model.Employee;
import com.stdu.zhpb.model.Preferences;
import com.stdu.zhpb.model.Store;
import com.stdu.zhpb.vo.PreferenceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@Service
public class PreferencesServiceImpl extends ServiceImpl<PreferenceMapper, Preferences> implements PreferencesService {

    @Autowired
    private StoreInfoFeignClient storeInfoFeignClient;

    @Override
    public Map<String, Object> findPagePreference(Page<Preferences> pageParam, PreferenceVo preferenceVo) {
        Store store = storeInfoFeignClient.getByName(preferenceVo.getStore());
        String storeId = store.getId();
        LambdaQueryWrapper<Preferences> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Preferences::getStoreId,storeId);

        Page<Preferences> preferencesPage = baseMapper.selectPage(pageParam, lambdaQueryWrapper);
        long totalCount = preferencesPage.getTotal();
        long totalPage = preferencesPage.getPages();

        List<Preferences> list = preferencesPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);

        return map;
    }
}
