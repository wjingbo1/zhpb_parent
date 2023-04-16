package com.stdu.zhpb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 林健强
 * @Date 2023/3/30 20:46
 * @Description: 获取
 */
public class GetWeekNum {
    public static int getWeekNum(String dateS) throws ParseException {
        String dateString = GetWeekOne.get(dateS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse(dateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            dayOfWeek = 7;
        } else {
            dayOfWeek--;
        }

        System.out.println(dayOfWeek);
        return dayOfWeek;
    }

}
