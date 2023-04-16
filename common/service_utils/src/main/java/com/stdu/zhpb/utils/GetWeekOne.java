package com.stdu.zhpb.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author 林健强
 * @Date 2023/3/30 19:49
 * @Description: 返回当前日期所在的星期的星期一的日期，因为要从每个周的周一开始遍历
 */
public class GetWeekOne {
        public static String get(String dateString){
            // 将字符串转换为LocalDate对象
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date = LocalDate.parse(dateString, formatter);

            // 获取该日期所在周的周一
            LocalDate monday = date.with(DayOfWeek.MONDAY);

            // 格式化输出
            String output = monday.format(formatter);
            System.out.println(output);  // 输出结果为: 2022/02/28
            return output;
        }
}
