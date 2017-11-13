package com.cherry.util;

import java.util.Random;

/**
 * 随机码工具类
 * Created by Administrator on 2017/11/10.
 */
public class KeyUtil {

    public static synchronized String genUniqueKey(){
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000; //生成6位随机数

        return System.currentTimeMillis() + String.valueOf(number);
        // 考虑到多线程同步 还需做synchronized处理
    }


}
