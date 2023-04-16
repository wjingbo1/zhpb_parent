package com.stdu.zhpb.client.employee;

import com.stdu.zhpb.model.Preferences;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@FeignClient(value = "service-employee")
public interface PerfrenceFeignClient {

    @ApiOperation("获取偏好信息")
    @GetMapping("/api/preference/inner/getAll")
    List<Preferences> getAll();

}
