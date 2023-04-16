package com.stdu.zhpb.schedule.service;

import com.stdu.zhpb.model.Duration;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
public interface DurationService {

    List<Duration> getDurationAll();

    boolean updateduration(double duration , String id);
}
