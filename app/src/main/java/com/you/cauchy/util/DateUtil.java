package com.you.cauchy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static String getTimeString(Date date) {
        if (null == date)
            return null;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8:00"));
        int HH = calendar.get(Calendar.HOUR_OF_DAY) ;
        int mm = calendar.get(Calendar.MINUTE);
        return HH + ":" + mm;
    }

    public static String getDateString(Date date) {
        if (null == date)
            return null;
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-8:00"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static void main(String[] args){
        System.out.print(" ======> " + DateUtil.getDateString(new Date()));
    }
}
