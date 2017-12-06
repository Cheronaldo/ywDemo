package com.cherry.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 * Created by Administrator on 2017/11/10.
 */
public class DateUtil {

    /**
     * 获取系统当前时间（标准时间）
     * @return
     */
    public static synchronized Date getDate(){
        Date date = new Date();
        return date;
    }

    /**
     * 校验设备操作校验码是否有效
     * @param date
     * @return
     */
    public static boolean ifDateValid(Date date){

        boolean b = false;

        Long oldTime = date.getTime();
        Long currentTime = System.currentTimeMillis();
        if(currentTime - oldTime <= 1800000){
            b = true;
        }

        return b;
    }

    /**
     * 字符串转Date
     * @param time
     * @return
     */
    public static synchronized Date convertString2Date(String time){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  date = sdf.parse(time);
            return date;
        }catch (Exception e){

        }
        return null;
    }

    /**
     * Date转字符串
     * @param date
     * @return
     */
    public static synchronized String convertDate2String(Date date){

        // TODO DateTimeFormatter

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);

    }

    /**
     * 历史数据查询 左时间为空处理
     * @param oldDateString
     * @return
     */
    public static synchronized Date oldDateHandle(String oldDateString){
        // 1.判断页面时间参数是否为 null
        boolean isOldDateNull = StringUtils.isEmpty(oldDateString);
        if (isOldDateNull){
            // 为空 则默认为最早记录的时间
            oldDateString = "2017-01-01 00:00:00";
        }
        return DateUtil.convertString2Date(oldDateString + ":00");
    }

    /**
     * 历史数据查询 右时间为空处理
     * @param newDateString
     * @return
     */
    public static synchronized Date newDateHandle(String newDateString){
        // 1.判断页面时间参数是否为 null
        boolean isNewDateNull = StringUtils.isEmpty(newDateString);
        if (isNewDateNull){
            // 为空则默认为当前系统时间
            return new Date();
        }

        return DateUtil.convertString2Date(newDateString + ":00");
    }

}
