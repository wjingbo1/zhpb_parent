package com.stdu.zhpb.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stdu.zhpb.model.Store;
import com.stdu.zhpb.store.mapper.StoreMapper;
import com.stdu.zhpb.store.service.StoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.zhpb.vo.StoreQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjingbo
 * @since 2023-03-19
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Override
    public Map<String, Object> findPageStore(Page<Store> pageParam, StoreQueryVo storeQueryVo) {

        String name = storeQueryVo.getName();
        String address = storeQueryVo.getAddress();

        LambdaQueryWrapper<Store> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(!StringUtils.isEmpty(name),Store::getName,name);
        lambdaQueryWrapper.like(!StringUtils.isEmpty(address),Store::getAddress,address);

        Page<Store> storePage = baseMapper.selectPage(pageParam, lambdaQueryWrapper);
        long totalCount = storePage.getTotal();
        long totalPage = storePage.getPages();
        List<Store> records = storePage.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;

    }
}
