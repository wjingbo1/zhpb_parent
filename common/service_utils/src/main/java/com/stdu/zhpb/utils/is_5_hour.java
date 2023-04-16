package com.stdu.zhpb.utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class is_5_hour {
    // 判断当前时间字符串str1是否比另一个字符串str2大，如果小，返回1；否则判断是否距离str2半个小时，如果是返回2，否则返回3
    public static int compare(String str1, String str2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm").withResolverStyle(ResolverStyle.LENIENT);
        LocalTime time1 = LocalTime.parse(str1, formatter);
        LocalTime time2 = LocalTime.parse(str2, formatter);
        if (time1.isBefore(time2)) {
            return 1;
        } else {
            Duration duration = Duration.between(time2, time1);
            long minutes = duration.toMinutes();

            if (minutes >= 30) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}
