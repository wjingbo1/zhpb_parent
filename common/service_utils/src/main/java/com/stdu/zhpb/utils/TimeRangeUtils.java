package com.stdu.zhpb.utils;

import java.time.LocalTime;

/**
 * @Author 林健强
 * @Date 2023/4/10 10:15
 * @Description: TODO
 */
public class TimeRangeUtils {
    /**
     * 判断第一个时间段是否完全包含第二个时间段
     * @param timeRange 第一个时间段字符串，格式为"HH:mm-HH:mm"
     * @param subTimeRange 第二个时间段字符串，格式为"HH:mm-HH:mm"
     * @return 如果第一个时间段完全包含第二个时间段，返回true；否则返回false
     */
    public static boolean isContained(String timeRange, String subTimeRange) {
        LocalTime startTime1 = LocalTime.parse(timeRange.split("-")[0]);
        LocalTime endTime1 = LocalTime.parse(timeRange.split("-")[1]);
        LocalTime startTime2 = LocalTime.parse(subTimeRange.split("-")[0]);
        LocalTime endTime2 = LocalTime.parse(subTimeRange.split("-")[1]);
        return (startTime1.isBefore(startTime2) || startTime1.equals(startTime2))
                && (endTime1.isAfter(endTime2) || endTime1.equals(endTime2));
    }
}