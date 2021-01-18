package com.example.yinian.menkou.yn_shipingtonghua.utils;

/**
 * Created by chenjun on 2017/4/10.
 */

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {

    public static String getTodayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }

    public static String nyr(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }
    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getTodayDateTimes() {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日",
                Locale.getDefault());
        return format.format(new Date());
    }


    public static void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }
    /**
     * 计算两个日期之间相隔的天数
     * @param begin
     * @param end
     * @return
     * @throws ParseException
     */
    public static long countDay(String begin, String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate , endDate;
        long day = 0;
        try {
            beginDate= format.parse(begin);
            endDate= format.parse(end);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }


    /** 代表数组里的年、月、日 */
    private static final int Y = 0, M = 1, D = 2;
    /**
     * 将代表日期的字符串分割为代表年月日的整形数组
     * @param date
     * @return
     */
    public static int[] splitYMD(String date){
        date = date.replace("-", "");
        int[] ymd = {0, 0, 0};
        ymd[Y] = Integer.parseInt(date.substring(0, 4));
        ymd[M] = Integer.parseInt(date.substring(4, 6));
        ymd[D] = Integer.parseInt(date.substring(6, 8));
        return ymd;
    }

    /**
     * 以循环的方式计算日期
     * @param beginDate endDate
     * @param
     * @return
     */
    public static List<String> getEveryday(String beginDate , String endDate){
        long days = countDay(beginDate, endDate);
        int[] ymd = splitYMD( beginDate );
        List<String> everyDays = new ArrayList<String>();
        everyDays.add(beginDate);
        for(int i = 0; i < days; i++){
            ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
            everyDays.add(formatYear(ymd[Y])+"-"+formatMonthDay(ymd[M])+"-"+formatMonthDay(ymd[D]));
        }
        return everyDays;
    }

    /**
     * 将不足两位的月份或日期补足为两位
     * @param decimal
     * @return
     */
    public static String formatMonthDay(int decimal){
        DecimalFormat df = new DecimalFormat("00");
        return df.format( decimal );
    }

    /**
     * 将不足四位的年份补足为四位
     * @param decimal
     * @return
     */
    public static String formatYear(int decimal){
        DecimalFormat df = new DecimalFormat("0000");
        return df.format( decimal );
    }

    /**
     * 日期加1天
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static int[] addOneDay(int year, int month, int day){
        if(isLeapYear( year )){
            day++;
            if( day > DAYS_P_MONTH_LY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }else{
            day++;
            if( day > DAYS_P_MONTH_CY[month -1 ] ){
                month++;
                if(month > 12){
                    year++;
                    month = 1;
                }
                day = 1;
            }
        }
        int[] ymd = {year, month, day};
        return ymd;
    }

    private static transient int gregorianCutoverYear = 1582;

    /** 闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_LY= {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /** 非闰年中每月天数 */
    private static final int[] DAYS_P_MONTH_CY= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    /**
     * 检查传入的参数代表的年份是否为闰年
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year >= gregorianCutoverYear ?
                ((year%4 == 0) && ((year%100 != 0) || (year%400 == 0))) : (year%4 == 0);
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date;
        long ll = 0;
        try {
            date = sdr.parse(time);
             ll = date.getTime();
           //  times = String.valueOf(l);
           // times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll;
    }

    public static String getTimestamp(String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    public static long date2TimeStamp(String date) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dater=sdf.parse(date);

            if (dater!=null){
                return dater.getTime();
            }else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }


    public static String datas(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
       // int i = Integer.parseInt(time);
        String times = sdr.format(lcc);
        return times;

    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timeHore(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyy_mm_dd_HH_mm_ss", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        // int i = Integer.parseInt(time);

        return sdr.format(new Date(lcc));

    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16:09"）
     *
     * @param time
     * @return
     */
    public static String timet(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm\nyyyy年MM月dd日", Locale.CHINA);
        long lcc = Long.parseLong(time);
        return sdr.format(new Date(lcc));

    }

    /**
     * @param //time斜杠分开
     * @return
     */
    public static String timeslash(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd,HH:mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * @param //time斜杠分开
     * @return
     */
    public static String timeslashData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
//      int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * @param //time斜杠分开
     * @return
     */
    public static String timeMinute(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
     //   int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));

    }

    public static String tim(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }
    public static String timett(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String time(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String timeb(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String ti(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String timeConversion(int time) {
        int hour = 0;
        int minutes = 0;
        int sencond = 0;
        int temp = time % 3600;
        if (time > 3600) {
            hour = time / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return (hour<10?("0"+hour):hour) + ":" + (minutes<10?("0"+minutes):minutes) + ":" + (sencond<10?("0"+sencond):sencond);
    }

    public static String tiee(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("mm HH", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String time3(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String time1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String time2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }

    public static String time22(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.parseLong(time);
//        int i = Integer.parseInt(time);
        return sdr.format(new Date(lcc));
    }


    // 调用此方法输入所要转换的时间戳例如（1402733340）输出（"2014年06月14日16时09分00秒"）
    public static String times(long timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日 - HH:mm", Locale.CHINA);
        return sdr.format(new Date(timeStamp));

    }

    public static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

    // 并用分割符把时间分成时间数组
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14-16-09-00"）
     *
     * @param time
     * @return
     */
    public static String timesOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
       // int i = Integer.parseInt(time);
        String times = sdr.format(lcc);
        return times;

    }

    public static String timesTwo(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
      //  int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }

    public static String xiaoshi(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH-mm", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        //  int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }

    public static String timesTwodian(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        //  int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }

    public static String timesRi(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM.dd", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
      //  int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    public static String timesNian(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
      //  int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc));
        return times;

    }

    /**
     * 并用分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public static String[] timestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] fenge = times.split("[年月日时分秒]");
        return fenge;
    }

    /**
     * 根据传递的类型格式化时间
     *
     * @param str
     * @param type
     *            例如：yy-MM-dd
     * @return
     */
    public static String getDateTimeByMillisecond(String str, String type) {

        Date date = new Date(Long.valueOf(str));

        SimpleDateFormat format = new SimpleDateFormat(type, Locale.CHINA);

        String time = format.format(date);

        return time;
    }

    /**
     * 分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public String[] division(String time) {

        String[] fenge = time.split("[年月日时分秒]");

        return fenge;

    }

    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static String changeweek(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取日期和星期　例如：２０１４－１１－１３　１１:００　星期一
     *
     * @param time
     * @param type
     * @return
     */
    public static String getDateAndWeek(String time, String type) {
        return getDateTimeByMillisecond(time + "000", type) + "  "
                + changeweekOne(time);
    }

    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static String changeweekOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 输入日期如（2014年06月14日16时09分00秒）返回（星期数）
     *
     * @param time
     * @return
     */
    public String week(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 输入日期如（2014-06-14-16-09-00）返回（星期数）
     *
     * @param time
     * @return
     */
    public String weekOne(String time) {
        Date date = null;
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }

    /**

     　　* 判断时间是否在时间段内 *

     　　* @param date

     　　* 当前时间 yyyy-MM-dd HH:mm:ss

     　　* @param strDateBegin

     　　* 开始时间 00:00:00

     　　* @param strDateEnd

     　　* 结束时间 00:05:00

     　　* @return

     　　*/

    public static boolean isInDate(Date date, String strDateBegin,

                                   String strDateEnd) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String strDate = sdf.format(date);

    // 截取当前时间时分秒

    int strDateH = Integer.parseInt(strDate.substring(11, 13));

    int strDateM = Integer.parseInt(strDate.substring(14, 16));

    int strDateS = Integer.parseInt(strDate.substring(17, 19));

    // 截取开始时间时分秒

    int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));

    int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));

    int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));

    // 截取结束时间时分秒

    int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));

    int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));

    int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));

    if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {

    // 当前时间小时数在开始时间和结束时间小时数之间

    if (strDateH > strDateBeginH && strDateH < strDateEndH) {

    return true;

    // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间

    } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM

    && strDateM <= strDateEndM) {

    return true;

    // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间

    } else if (strDateH == strDateBeginH && strDateM == strDateBeginM

    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {

    return true;

    }

    // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数

    else if (strDateH >= strDateBeginH && strDateH == strDateEndH

    && strDateM <= strDateEndM) {

    return true;

    // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数

    } else if (strDateH >= strDateBeginH && strDateH == strDateEndH

    && strDateM == strDateEndM && strDateS <= strDateEndS) {

    return true;

    } else {

    return false;

    }

    } else {

    return false;

    }

    }
    }
