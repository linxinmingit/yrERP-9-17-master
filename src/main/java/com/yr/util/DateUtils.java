package com.yr.util;

import com.yr.user.controller.UserController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    /**
     * 将时间字符串转换为Date类型
     * @param dateStr
     * @return Date
     */
    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        try {
            date = formater.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 返回java.sql.Date格式的
     * @param strDate
     * */
    public static java.sql.Date strToDate(String strDate) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }

    /**
     * Date类型的时间转String类型的时间,格式为指定格式
     *
     * @param date
     * @param formatStr
     * @return String 格式为:formatStr
     */
    public static String dateToStr(Date date, String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        return formatter.format(date);
    }


    /**
     *使用Calendar对象计算时间差，可以按照需求定制自己的计算逻辑
     * @param date
     * @throws Exception
     */
    public static int calculateTimeDifferenceByCalendar(Date date){
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int year = 0;
        int oldYear = 0;
        try {
            //Date date = formatter.parse(strDate);
            Calendar c1 = Calendar.getInstance();   //当前日期
            Calendar c2 = Calendar.getInstance();
            //c2.setTime(date);   //设置为另一个时间

            year = c1.get(Calendar.YEAR);
            oldYear = c2.get(Calendar.YEAR);

            //这里只是简单的对两个年份数字进行相减，而没有考虑月份的情况
            //System.out.println("传入的日期与今年的年份差为：" + (year - oldYear));
        }catch (Exception e){
            e.printStackTrace();
        }
        return year - oldYear;
    }
}

