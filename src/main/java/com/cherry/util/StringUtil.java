package com.cherry.util;

/**
 * 字符串处理工具类
 * Created by Administrator on 2017/12/03.
 */
public class StringUtil {

    public static char[] string2CharList(String mask){

        char[] strategyList = new char[mask.length()];
        strategyList = mask.toCharArray();
        return strategyList;
    }

}
