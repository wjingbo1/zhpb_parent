package com.stdu.zhpb.schedule.controller;

import com.stdu.zhpb.model.EmployeeSchedulingResult;
import com.stdu.zhpb.result.Result;
import com.stdu.zhpb.schedule.service.SchedulingResultService;
import com.stdu.zhpb.utils.GetWeekOne;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjingbo
 * @since 2023-03-19
 */

@Api(tags = "排班管理")
@RestController
@RequestMapping("/admin/schedule")
public class ScheduleController {

    @Autowired
    private SchedulingResultService schedulingResultService;

    @ApiOperation("获取一天的排班结果")
    @GetMapping("/getCurrentDayResult")
    public Result<ArrayList<EmployeeSchedulingResult>> getCurrentDayResult(String id,String currentDate) throws ParseException {
        ArrayList<EmployeeSchedulingResult> currentDayResult = schedulingResultService.getCurrentDayResult(currentDate);
        if(currentDayResult.size()==0)
        {
            String s = GetWeekOne.get(currentDate);
            schedulingResultService.schedulingAlgorithm(s);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.parse(s, formatter);
            for (int i=1;i<=7;i++){
                String time = date.plusDays(i - 1).format(formatter);
                schedulingResultService.restResult(time);
            }
            //安排休息时间
            currentDayResult=schedulingResultService.getCurrentDayResult(currentDate);
        }
        return Result.ok(currentDayResult);
    }

    @ApiOperation("获取一周的排班结果")
    @GetMapping("/getCurrentWeekResult")
    public Result<ArrayList<EmployeeSchedulingResult>>getCurrentWeekResult(String id,String currentDate) throws ParseException {
        String s = GetWeekOne.get(currentDate);
        ArrayList<EmployeeSchedulingResult> currentDayResult = schedulingResultService.getCurrentDayResult(currentDate);
        ArrayList<EmployeeSchedulingResult> currentWeekResult = schedulingResultService.getCurrentWeekResult(s);
        if(currentDayResult.size()==0)
        {
            schedulingResultService.schedulingAlgorithm(s);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.parse(s, formatter);
            for (int i=1;i<=7;i++){
                String time = date.plusDays(i - 1).format(formatter);
                schedulingResultService.restResult(time);
            }
            //安排休息时间
            currentWeekResult=schedulingResultService.getCurrentWeekResult(s);
        }
        return Result.ok(currentWeekResult);
    }
}
