package com.stdu.zhpb.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.zhpb.model.Rule;

import java.util.Date;
import java.util.List;

public interface RuleService extends IService<Rule> {

    List<Integer> dayCount(String storeId , Date date);


}
