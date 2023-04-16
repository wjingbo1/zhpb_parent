package com.stdu.zhpb.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stdu.zhpb.model.EmployeeSchedulingResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@Mapper
public interface SchedulingResultMapper {

    List<EmployeeSchedulingResult> getByCurrentDate(String currentDate);

    List<EmployeeSchedulingResult>getByEmployeeId(String id,String currentDate);

    void addSchedulingResult(EmployeeSchedulingResult employeeSchedulingResult);

    void updateCurrentDayDuration(String id, double v,String currentDate);
    void addResult(int id,String status);
}
