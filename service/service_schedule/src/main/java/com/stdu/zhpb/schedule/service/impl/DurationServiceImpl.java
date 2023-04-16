package com.stdu.zhpb.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.zhpb.model.Duration;
import com.stdu.zhpb.schedule.mapper.DurationMapper;
import com.stdu.zhpb.schedule.service.DurationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@Service
public class DurationServiceImpl extends ServiceImpl<DurationMapper, Duration> implements DurationService {


    @Override
    public List<Duration> getDurationAll() {
        List<Duration> durationList = baseMapper.selectList(new QueryWrapper<Duration>().orderByAsc("total_duration"));
        return durationList;
    }

    @Override
    public boolean updateduration(double duration, String id) {

        boolean isSuccess = update().setSql("total_duration = total_duration + " + duration).eq("id", id).update();
        return isSuccess;
    }
}
