package com.stdu.zhpb.utils;

/**
 * @Author 林健强
 * @Date 2023/4/4 16:04
 * @Description: TODO
 */
public class TimeUtil {
    //将字符串7-9改成7:00-9:00
    public static String convertTimeFormat(String timeString) {
        String convertedString = timeString.replace("-", ":00-");
        String[] times = convertedString.split(":");
        if (times.length == 2) {
            return convertedString + ":00";
        } else {
            times[2] = "00";
            return String.join(":", times);
        }
    }
}
