package com.stdu.zhpb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 林健强
 * @Date 2023/4/12 10:32
 * @Description: TODO
 */
public class Is_Has_Time {

    public static void main(String[] args) {
        String timeA = "12:00-15:00";
        String timeB = "11:00-14:00";
        boolean isContain =isTimeRangeContained(timeA, timeB);
        System.out.println(isContain); //输出：true
        String timeStr = "17:05";
        String newTimeStr = addHalfHours(timeStr,2);
        System.out.println(newTimeStr); // 输出: 17:30
    }
    public static boolean isTimeRangeContained(String timeRangeA, String timeRangeB) {
        String[] partsA = timeRangeA.split("-");
        String[] partsB = timeRangeB.split("-");

        LocalTime startTimeA = parseTime(partsA[0]);
        LocalTime endTimeA = parseTime(partsA[1]);
        LocalTime startTimeB = parseTime(partsB[0]);
        LocalTime endTimeB = parseTime(partsB[1]);

        return startTimeA.compareTo(startTimeB) <= 0 && endTimeA.compareTo(endTimeB) >= 0;
    }

    private static LocalTime parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return LocalTime.of(hour, minute);
    }

    public static String addHalfHours(String strTime, int n) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date timeObj = sdf.parse(strTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(timeObj);
            cal.add(Calendar.MINUTE, 30*n);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
