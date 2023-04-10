package com.stdu.zhpb.store.api;

import com.stdu.zhpb.model.Store;
import com.stdu.zhpb.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */

@Api(tags = "店铺信息")
@RestController
@RequestMapping("/api/employee")
public class StoreInfoApiController {

    @Autowired
    private StoreService storeService;

    @ApiOperation("获取店铺信息")
    @GetMapping("inner/getByName/{name}")
    public Store getByName(@PathVariable String name){
        Store store = storeService.getByName(name);
        return store;
    }
}
