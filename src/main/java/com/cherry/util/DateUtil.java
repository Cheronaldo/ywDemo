package com.cherry.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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

    public static boolean ifDateValid(Date date){

        boolean b = false;

        Long oldTime = date.getTime();
        Long currentTime = System.currentTimeMillis();
        if(currentTime - oldTime <= 1800000){
            b = true;
        }

        return b;
    }


}
