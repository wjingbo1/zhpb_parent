package com.stdu.zhpb.store.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stdu.zhpb.model.Store;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.zhpb.vo.StoreQueryVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjingbo
 * @since 2023-03-19
 */
public interface StoreService extends IService<Store> {

    Map<String, Object> findPageStore(Page<Store> pageParam, StoreQueryVo storeQueryVo);
}
