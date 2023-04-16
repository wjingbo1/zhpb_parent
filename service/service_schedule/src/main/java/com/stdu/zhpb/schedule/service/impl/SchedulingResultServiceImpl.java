package com.stdu.zhpb.schedule.service.impl;

import com.stdu.zhpb.client.employee.PerfrenceFeignClient;
import com.stdu.zhpb.model.Duration;
import com.stdu.zhpb.model.EmployeeSchedulingResult;
import com.stdu.zhpb.model.Preferences;
import com.stdu.zhpb.schedule.mapper.SchedulingResultMapper;
import com.stdu.zhpb.schedule.service.RuleService;
import com.stdu.zhpb.schedule.service.SchedulingResultService;
import com.stdu.zhpb.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@Service
public class SchedulingResultServiceImpl implements SchedulingResultService {

    @Resource
    private RuleService ruleService;

    @Autowired
    private DurationServiceImpl durationService;

    @Autowired
    private SchedulingResultMapper SchedulingResultMapper;

    @Autowired
    private PerfrenceFeignClient perfrenceFeignClient;


    private ArrayList<Preferences> employeePreferences = new ArrayList<>();//存储所有员工偏好信息
    HashMap<String, ArrayList<Preferences>> arrangeResult = new HashMap<>();//存储一的排班结果
    public int[][] getArrangeTime(Integer[] n) {
        return PersonTime.getArrangeTime(n);
    }//获取待安排时间段的二维数组
    public int[][] getArrangeTimeWeekend(Integer[] integers){
        return PersonTimeWeekend.getArrangeTime(integers);
    }//获取待安排时间段的二维数组
    public void getEmployeePreferences() {
        this.employeePreferences = (ArrayList<Preferences>) perfrenceFeignClient.getAll();//查询所有员工偏好信息
        //System.out.println(employeePreferences.toString());
    }
    public ArrayList<Preferences> getCurrentEmployeePreferences(String workdayPreference) {
        getEmployeePreferences();//查询所有员工偏好
        ArrayList<Preferences> CurrentEmployeePreferences = new ArrayList<>();
        //将在周一有偏好的员工放进数组
        for (Preferences employeePreference : employeePreferences) {
            boolean has_workDay = Is_Has_WorkDay.is_Has_WorkDay(employeePreference.getWorkdayPreference(), workdayPreference);
            if (has_workDay)
                CurrentEmployeePreferences.add(employeePreference);
        }
        return CurrentEmployeePreferences;
    }//按照周几筛选当天啊有偏好的员工，例如筛选周一有工作偏好的员工
    public void schedulingAlgorithm(String dateString) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        for (int i = 1; i <= 7; i++) {
            String curDay = date.plusDays(i - 1).format(formatter);
            List<Integer> list = ruleService.dayCount("1yVTqDvQU", curDay);
            Integer[] integers = list.stream().toArray(value -> new Integer[value]);
            int arrangeTime[][]=new int[1000][1000];
            //从数据库拿到当天客流量的二维数组
            if(Is_Weekend.isWeekend(curDay)){
                arrangeTime= getArrangeTimeWeekend(integers);
            }
            else {
                arrangeTime= getArrangeTime(integers);
            }
            //获取待安排的二维数组
            HashMap<String, ArrayList<Preferences>> map = new HashMap<>();//存储当天时间段安排的权重信息
            HashMap<String, ArrayList<EmployeeSchedulingResult>> currentResult = new HashMap<>();//存储当天时间段安排的结果
            //按照周几筛选当天有偏好的员工
            ArrayList<Preferences> currentEmployeePreferences = getCurrentEmployeePreferences(String.valueOf(i));
            //如果当日员工偏好为空，从所有员工中按照安排时间的升序排序进行随机安排
            //执行代码，随机进行安排，查询数据库
            if (currentEmployeePreferences.size() == 0) {
                //System.out.println(byWorkingDuration.toString());
                for (int j = 0; j < 200; j++)
                    for (int k = 0; k < 200; k++) {
                        if (arrangeTime[j][k] > 0) {
                            System.out.println();
                            //从数据库中查询所有员工的工作时长进行从小到大排序
                            List<Duration> byWorkingDuration = durationService.getDurationAll();
                            String currentEmployeeWorkingTime = TimeUtil.convertTimeFormat(String.valueOf(j) + "-" + String.valueOf(k));//将7-9转换成7：00-9：00
                            ArrayList<EmployeeSchedulingResult> employeeSchedulingResults = currentResult.computeIfAbsent(currentEmployeeWorkingTime, k1 -> new ArrayList<>());
                            for (Duration duration : byWorkingDuration) {
                                boolean flag = false;
                                //查询数据库，查询在该时段前面四个半小时内的所有人员
                                String currentDate = date.plusDays(i - 1).format(formatter);
                                List<EmployeeSchedulingResult> byCurrentDate = SchedulingResultMapper.getByCurrentDate(currentDate);
                                List<EmployeeSchedulingResult> byEmployeeId = SchedulingResultMapper.getByEmployeeId(duration.getId(), currentDate);
                                boolean is_empty = false;
                                if (byCurrentDate.size() == 0)
                                    is_empty = true;
                                if (!is_empty) {
                                    double currentDayDuration;
                                    if (byEmployeeId.size() == 0)
                                        currentDayDuration = 0.0;
                                    else currentDayDuration = byEmployeeId.get(0).getCurrentDayDuration();
                                    for (EmployeeSchedulingResult employeeSchedulingResult : byCurrentDate) {
                                        int secondDashIndex = employeeSchedulingResult.getTime().indexOf("-", employeeSchedulingResult.getTime().indexOf("-") + 1);
                                        String arrangedTime = employeeSchedulingResult.getTime().substring(secondDashIndex + 1);
                                        int index = currentEmployeeWorkingTime.indexOf('-');
                                        String result = currentEmployeeWorkingTime.substring(0, index);
                                        System.out.println(result + "-----" + arrangedTime);
                                        System.out.println();
                                        int compareResult = is_5_hour.compare(result, arrangedTime);
                                        if (compareResult == 2 && currentDayDuration + k - j <= 8.0) {
                                            flag = true;
                                            break;
                                        } else if (compareResult == 1) {
                                            flag = false;
                                            //break;
                                        } else if (compareResult == 3 && currentDayDuration + k - j <= 8.0 && !employeeSchedulingResult.getEmployeeId().equals(duration.getId())) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                                if (is_empty) flag = true;
                                if (flag) {
                                    System.out.println();
                                    if (byEmployeeId.size() == 0) {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = new EmployeeSchedulingResult();
                                        employeeSchedulingResult.setEmployeeId(duration.getId());
                                        employeeSchedulingResult.setName(duration.getName());
                                        employeeSchedulingResult.setPosition(duration.getPosition());
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(k - j);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                    } else {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = byEmployeeId.get(0);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(employeeSchedulingResult.getCurrentDayDuration() + k - j);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                        SchedulingResultMapper.updateCurrentDayDuration(employeeSchedulingResult.getEmployeeId(), employeeSchedulingResult.getCurrentDayDuration(), currentDate);
                                    }
                                    //进行数据库操作，增加总工作时长，加入工作安排数据库
                                    arrangeTime[j][k]--;
                                    durationService.updateduration(k - j, duration.getId());
                                    if (arrangeTime[j][k] == 0) break;
                                }
                            }
                            System.out.println(currentResult.get(currentEmployeeWorkingTime).toString());
                        }
                    }

            } else {
                //该模块已成功，测试成功如果当日员工偏好不为空，计算偏好执行权重,计算在f[i][j]时间段内员工偏好权重的集合
                for (int j = 0; j < 200; j++)
                    for (int k = 0; k < 200; k++) {
                        if (arrangeTime[j][k] > 0) {
                            String str1 = String.valueOf(j) + "-" + String.valueOf(k);
                            ArrayList<Preferences> prefsList = map.computeIfAbsent(str1, k1 -> new ArrayList<>());
                            // 如果该key还没有对应的ArrayList，则先创建一个空的ArrayList
                            // 将新的EmployeePreferences对象添加到ArrayList中
                            for (Preferences currentEmployeePreference : currentEmployeePreferences) {
                                String str2 = currentEmployeePreference.getWorkingPreferences();
                                double weight = WeightCalculation.getWeight(str2, str1);
                                currentEmployeePreference.setWorkingWeight(weight);
                                // 获取名为"John"的ArrayList
                                prefsList.add(currentEmployeePreference);
                            }
                            map.put(str1, prefsList);
                            //对集合里的权重进行从大到小的排序
                            ArrayList<Preferences> prefsListOrderByWeight = map.get(str1);
                            prefsListOrderByWeight.sort(Comparator.comparingDouble(Preferences::getWorkingWeight).reversed());
                            map.put(str1, prefsListOrderByWeight);
                        }
                    }
                for (int j = 0; j <= 200; j++)
                    for (int k = 0; k <= 200; k++) {
                        if (arrangeTime[j][k] > 0) {
                            String str = String.valueOf(j) + "-" + String.valueOf(k);
                            ArrayList<Preferences> employeePreferences1 = map.get(str);
                            String currentEmployeeWorkingTime = TimeUtil.convertTimeFormat(str);//将7-9转换成7：00-9：00
                            ArrayList<EmployeeSchedulingResult> employeeSchedulingResults = currentResult.computeIfAbsent(currentEmployeeWorkingTime, k1 -> new ArrayList<>());
                            for (Preferences preferences : employeePreferences1) {
                                boolean flag = false;
                                //查询数据库，查询在该时段前面四个半小时内的所有人员
                                String currentDate = date.plusDays(i - 1).format(formatter);
                                List<EmployeeSchedulingResult> byCurrentDate = SchedulingResultMapper.getByCurrentDate(currentDate);
                                List<EmployeeSchedulingResult> byEmployeeId = SchedulingResultMapper.getByEmployeeId(preferences.getId(), currentDate);
                                boolean is_empty = false;
                                if (byCurrentDate.size() == 0)
                                    is_empty = true;
                                if (!is_empty) {
                                    double currentDayDuration;
                                    if (byEmployeeId.size() == 0)
                                        currentDayDuration = 0.0;
                                    else currentDayDuration = byEmployeeId.get(0).getCurrentDayDuration();
                                    for (EmployeeSchedulingResult employeeSchedulingResult : byCurrentDate) {
                                        int secondDashIndex = employeeSchedulingResult.getTime().indexOf("-", employeeSchedulingResult.getTime().indexOf("-") + 1);
                                        String arrangedTime = employeeSchedulingResult.getTime().substring(secondDashIndex + 1);
                                        int index = currentEmployeeWorkingTime.indexOf('-');
                                        String result = currentEmployeeWorkingTime.substring(0, index);
                                        System.out.println(result + "-----" + arrangedTime);
                                        System.out.println();
                                        int compareResult = is_5_hour.compare(result, arrangedTime);
                                        if (compareResult == 2 && currentDayDuration + k - j <= 8.0) {
                                            flag = true;
                                            //break;
                                        } else if (compareResult == 1 && employeeSchedulingResult.getEmployeeId().equals(preferences.getId())) {
                                            flag = false;
                                            //break;
                                        } else if (compareResult == 3 && currentDayDuration + k - j <= 8.0 && !employeeSchedulingResult.getEmployeeId().equals(preferences.getId())) {
                                            flag = true;
                                            // break;
                                        }
                                    }
                                }
                                if (is_empty) flag = true;
                                if (flag) {
                                    System.out.println();
                                    if (byEmployeeId.size() == 0) {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = new EmployeeSchedulingResult();
                                        employeeSchedulingResult.setEmployeeId(preferences.getId());
                                        employeeSchedulingResult.setName(preferences.getName());
                                        employeeSchedulingResult.setPosition(preferences.getPosition());
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(k - j);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                    } else {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = byEmployeeId.get(0);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(employeeSchedulingResult.getCurrentDayDuration() + k - j);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                        SchedulingResultMapper.updateCurrentDayDuration(employeeSchedulingResult.getEmployeeId(), employeeSchedulingResult.getCurrentDayDuration(), currentDate);
                                    }
                                    //进行数据库操作，增加总工作时长，加入工作安排数据库
                                    arrangeTime[j][k]--;
                                    durationService.updateduration(k - j, preferences.getId());
                                    if (arrangeTime[j][k] == 0) break;
                                }

                            }
                        }

                    }
                /**
                 * 偏好执行完毕，发现当天还剩余为排班人员，执行随机安排
                 */
                for (int j = 0; j <= 200; j++)
                    for (int k = 0; k <= 200; k++) {
                        if (arrangeTime[j][k] > 0) {
                            //System.out.println(byWorkingDuration.toString());
                            System.out.println();
                            //从数据库中查询所有员工的工作时长进行从小到大排序
                            List<Duration> byWorkingDuration = durationService.getDurationAll();
                            String currentEmployeeWorkingTime = TimeUtil.convertTimeFormat(String.valueOf(j) + "-" + String.valueOf(k));//将7-9转换成7：00-9：00
                            ArrayList<EmployeeSchedulingResult> employeeSchedulingResults = currentResult.computeIfAbsent(currentEmployeeWorkingTime, k1 -> new ArrayList<>());
                            for (Duration duration : byWorkingDuration) {
                                boolean flag = false;
                                //查询数据库，查询在该时段前面四个半小时内的所有人员
                                String currentDate = date.plusDays(i - 1).format(formatter);
                                List<EmployeeSchedulingResult> byCurrentDate = SchedulingResultMapper.getByCurrentDate(currentDate);
                                List<EmployeeSchedulingResult> byEmployeeId = SchedulingResultMapper.getByEmployeeId(duration.getId(), currentDate);
                                boolean is_empty = false;
                                if (byCurrentDate.size() == 0)
                                    is_empty = true;
                                if (!is_empty) {
                                    double currentDayDuration;
                                    if (byEmployeeId.size() == 0)
                                        currentDayDuration = 0.0;
                                    else currentDayDuration = byEmployeeId.get(0).getCurrentDayDuration();
                                    for (EmployeeSchedulingResult employeeSchedulingResult : byCurrentDate) {
                                        int secondDashIndex = employeeSchedulingResult.getTime().indexOf("-", employeeSchedulingResult.getTime().indexOf("-") + 1);
                                        String arrangedTime = employeeSchedulingResult.getTime().substring(secondDashIndex + 1);
                                        int index = currentEmployeeWorkingTime.indexOf('-');
                                        String result = currentEmployeeWorkingTime.substring(0, index);
                                        System.out.println(result + "-----" + arrangedTime);
                                        System.out.println();
                                        int compareResult = is_5_hour.compare(result, arrangedTime);
                                        if (compareResult == 2 && currentDayDuration + k - j <= 8.0) {
                                            flag = true;
                                            //break;
                                        } else if (compareResult == 1 && employeeSchedulingResult.getEmployeeId().equals(duration.getId())) {
                                            flag = false;
                                            //break;
                                        } else if (compareResult == 3 && currentDayDuration + k - j <= 8.0 && !employeeSchedulingResult.getEmployeeId().equals(duration.getId())) {
                                            flag = true;
                                            // break;
                                        }
                                    }
                                }
                                if (is_empty) flag = true;
                                if (flag) {
                                    System.out.println();
                                    if (byEmployeeId.size() == 0) {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = new EmployeeSchedulingResult();
                                        employeeSchedulingResult.setEmployeeId(duration.getId());
                                        employeeSchedulingResult.setName(duration.getName());
                                        employeeSchedulingResult.setPosition(duration.getPosition());
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(k - j);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                    } else {
                                        String time = date.plusDays(i - 1).format(formatter) + "-" + currentEmployeeWorkingTime;
                                        EmployeeSchedulingResult employeeSchedulingResult = byEmployeeId.get(0);
                                        employeeSchedulingResult.setTime(time);
                                        employeeSchedulingResult.setSingleDuration(k - j);
                                        employeeSchedulingResult.setCurrentDayDuration(employeeSchedulingResult.getCurrentDayDuration() + k - j);
                                        employeeSchedulingResults.add(employeeSchedulingResult);
                                        currentResult.put(currentEmployeeWorkingTime, employeeSchedulingResults);
                                        SchedulingResultMapper.addSchedulingResult(employeeSchedulingResult);
                                        SchedulingResultMapper.updateCurrentDayDuration(employeeSchedulingResult.getEmployeeId(), employeeSchedulingResult.getCurrentDayDuration(), currentDate);
                                    }
                                    //进行数据库操作，增加总工作时长，加入工作安排数据库
                                    arrangeTime[j][k]--;
                                    durationService.updateduration(k - j, duration.getId());
                                    if (arrangeTime[j][k] == 0) break;
                                }
                            }
                            System.out.println(currentResult.get(currentEmployeeWorkingTime).toString());
                        }

                    }
            }
        }
    }
    //返回一天的排班结果
    public ArrayList<EmployeeSchedulingResult> getCurrentDayResult(String currentDate) {
        List<EmployeeSchedulingResult> currentDay = SchedulingResultMapper.getByCurrentDate(currentDate);
        return (ArrayList<EmployeeSchedulingResult>) currentDay;
    }

    //    public HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> getCurrentDayResult(String currentDate) {
//        HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> currentDayResult = new HashMap<>();
//        HashMap<String, ArrayList<EmployeeSchedulingResult>> currentTime = new HashMap<>();
//        List<EmployeeSchedulingResult> currentDay = resultMapper.getCurrentDay(currentDate);
//        for (EmployeeSchedulingResult employeeSchedulingResult : currentDay) {
//            String time = employeeSchedulingResult.getTime();
//            ArrayList<EmployeeSchedulingResult> employeeSchedulingResults = currentTime.computeIfAbsent(time, k1 -> new ArrayList<>());
//            employeeSchedulingResults.add(employeeSchedulingResult);
//            currentTime.put(time, employeeSchedulingResults);
//        }
//        HashMap<String, ArrayList<EmployeeSchedulingResult>> stringArrayListHashMap = currentDayResult.computeIfAbsent(currentDate, k1 -> new HashMap<>());
//        currentDayResult.put(currentDate, currentTime);
//        return currentDayResult;
//    }
//    public HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> getCurrentWeekResult(String currentDate) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDate date = LocalDate.parse(currentDate, formatter);
//        HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> currentDayResult = new HashMap<>();
//        for (int i = 1; i <= 7; i++) {
//            String time = date.plusDays(i - 1).format(formatter);
//            HashMap<String, ArrayList<EmployeeSchedulingResult>> currentTime = new HashMap<>();
//            List<EmployeeSchedulingResult> currentDay = resultMapper.getCurrentDay(time);
//            for (EmployeeSchedulingResult employeeSchedulingResult : currentDay) {
//                String time1 = employeeSchedulingResult.getTime();
//                ArrayList<EmployeeSchedulingResult> employeeSchedulingResults = currentTime.computeIfAbsent(time, k1 -> new ArrayList<>());
//                employeeSchedulingResults.add(employeeSchedulingResult);
//                currentTime.put(time1, employeeSchedulingResults);
//            }
//            HashMap<String, ArrayList<EmployeeSchedulingResult>> stringArrayListHashMap = currentDayResult.computeIfAbsent(time, k1 -> new HashMap<>());
//            currentDayResult.put(time, currentTime);
//        }
//        return currentDayResult;
//    }
    public ArrayList<EmployeeSchedulingResult>getCurrentWeekResult(String currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(currentDate, formatter);
        HashMap<String, HashMap<String, ArrayList<EmployeeSchedulingResult>>> currentDayResult = new HashMap<>();
        ArrayList<EmployeeSchedulingResult>arrayList=new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            String time = date.plusDays(i - 1).format(formatter);
            HashMap<String, ArrayList<EmployeeSchedulingResult>> currentTime = new HashMap<>();
            List<EmployeeSchedulingResult> currentDay = SchedulingResultMapper.getByCurrentDate(time);
            for (EmployeeSchedulingResult employeeSchedulingResult : currentDay) {
                arrayList.add(employeeSchedulingResult);
            }

        }
        return arrayList;
    }
    public void restResult(String currentDate){
        List<EmployeeSchedulingResult> byCurrentDate = SchedulingResultMapper.getByCurrentDate(currentDate);
        List<EmployeeSchedulingResult> middayByCurrentDate = new ArrayList<>();
        List<EmployeeSchedulingResult> nightByCurrentDate = new ArrayList<>();
        List<EmployeeSchedulingResult> middayByCurrentDateR = new ArrayList<>();
        List<EmployeeSchedulingResult> nightByCurrentDateR = new ArrayList<>();
        for (EmployeeSchedulingResult employeeSchedulingResult : byCurrentDate) {
            String str = employeeSchedulingResult.getTime();
            String[] arr = str.split("-");
            String result = str.substring(arr[0].length() + 1);
            if(Is_Has_Time.isTimeRangeContained(result,"11:00-14:00")){
                System.out.println(employeeSchedulingResult.toString());
                middayByCurrentDate.add(employeeSchedulingResult);
            }

            if(Is_Has_Time.isTimeRangeContained(result,"17:00-20:00"))
            {
                System.out.println(employeeSchedulingResult.toString());
                nightByCurrentDate.add(employeeSchedulingResult);
            }
        }
        String midTime="11:00";
        String nightTime="17:00";
        int len_mid=middayByCurrentDate.size();
        int len_night=nightByCurrentDate.size();
        int add_len_mid=len_mid/6+1;
        int add_len_night=len_night/6+1;
        for(int i=0;i<middayByCurrentDate.size();i+=add_len_mid){
            int r=i+add_len_mid;
            midTime=Is_Has_Time.addHalfHours(midTime,i/add_len_mid);
            System.out.println(midTime);
            for(int j=i;j<r&&j<middayByCurrentDate.size();j++){
                EmployeeSchedulingResult employeeSchedulingResult = middayByCurrentDate.get(j);
                String f_b=Is_Has_Time.addHalfHours(midTime,1);
                System.out.println(f_b+"中五");
                employeeSchedulingResult.setStatus(midTime+"-"+f_b+" 吃饭");
                middayByCurrentDateR.add(employeeSchedulingResult);
            }
        }
        for(int i=0;i<nightByCurrentDate.size();i+=add_len_night){
            int r=i+add_len_night;
            nightTime=Is_Has_Time.addHalfHours(nightTime,i/add_len_night);
            for(int j=i;j<r&&j<nightByCurrentDate.size();j++){
                EmployeeSchedulingResult employeeSchedulingResult=nightByCurrentDate.get(j);
                String f_b=Is_Has_Time.addHalfHours(nightTime,1);
                employeeSchedulingResult.setStatus(nightTime+"-"+f_b+" 吃饭");
                nightByCurrentDateR.add(employeeSchedulingResult);
            }
        }
        System.out.println(middayByCurrentDateR.size()+"返回公布结果");
        for(int i=0;i<middayByCurrentDateR.size();i++){
            SchedulingResultMapper.addResult(middayByCurrentDateR.get(i).getId(),middayByCurrentDateR.get(i).getStatus());
        }
        for(int i=0;i<nightByCurrentDateR.size();i++){
            SchedulingResultMapper.addResult(nightByCurrentDateR.get(i).getId(),nightByCurrentDateR.get(i).getStatus());
        }
    }

}
