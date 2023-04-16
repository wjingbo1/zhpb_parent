package com.stdu.zhpb.schedule.service;

import com.stdu.zhpb.model.EmployeeSchedulingResult;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
public interface SchedulingResultService {

    void getEmployeePreferences();
    //获取当天安排结果
    //HashMap<String, HashMap<String , ArrayList<EmployeeSchedulingResult>>>getCurrentDayResult(String currentDate);
    ArrayList<EmployeeSchedulingResult> getCurrentDayResult(String currentDate);
    //获取本周安排结果
    //public HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> getCurrentWeekResult(String currentDate);
    public ArrayList<EmployeeSchedulingResult> getCurrentWeekResult(String currentDate);


    public void schedulingAlgorithm(String dateString) throws ParseException;
    public void restResult(String currentDate);

}
