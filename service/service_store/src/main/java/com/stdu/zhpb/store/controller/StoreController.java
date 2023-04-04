package com.stdu.zhpb.store.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stdu.zhpb.model.Store;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.store.service.StoreService;
import com.stdu.zhpb.vo.StoreQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjingbo
 * @since 2023-03-19
 */

@Api(tags = "店铺管理")
@RestController
@RequestMapping("/admin/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @ApiOperation("店铺列表")
    @GetMapping("{page}/{limit}")
    public Result getStoreList(@PathVariable Long page,
                               @PathVariable Long limit,
                               StoreQueryVo storeQueryVo){
        Page<Store> pageParam = new Page<>(page,limit);
        Map<String,Object> map = storeService.findPageStore(pageParam,storeQueryVo);
        return Result.ok(map);
    }

    @ApiOperation("添加店铺")
    @PostMapping("/add")
    public Result addStore(@RequestBody Store store){
        return storeService.addStore(store);
    }

    @PutMapping
    @ApiOperation("更新店铺")
    public Result updateStore(@RequestBody Store store){
        return storeService.updateStore(store);
    }

    @ApiOperation("删除店铺")
    @DeleteMapping
    public Result deleteStore(@RequestParam String id){
        return storeService.deleteStore(id);
    }


}

