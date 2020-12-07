package com.aaa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 * @date 2019/11/20   
 * 时间工具类
**/ 
public class DateUtils {
    /**
     * 指定格式
     */
    private final static String DATE_TO_STRING_FORMAT = "yyyy-MM-dd";
    static SimpleDateFormat format = new SimpleDateFormat("MM");
    static Calendar cal = Calendar.getInstance();



    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String toFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TO_STRING_FORMAT);
        String res = sdf.format(date);
        return res;
    }


    /**
     * 获取近一年的月份
     */
    public static int[] getMonthByNearYear(){
        int[] ints = new int[12];
        for (int i = ints.length - 1; i >= 0 ; i--) {
            cal.setTime(new Date());
            cal.add(Calendar.MONTH,-i);
            Date m3 = cal.getTime();
            String mon = format.format(m3);
            ints[11 - i] = Integer.parseInt(mon);
        }
        return ints;
    }

    public void test(){
        int[] ints = getMonthByNearYear();
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

}
