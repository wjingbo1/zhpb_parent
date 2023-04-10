package com.stdu.zhpb.client.store;

import com.stdu.zhpb.model.Store;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@FeignClient(value = "service-store")
public interface StoreInfoFeignClient {

    @ApiOperation("获取店铺信息")
    @GetMapping("/api/employee/inner/getByName/{name}")
    Store getByName(@PathVariable String name);
}
