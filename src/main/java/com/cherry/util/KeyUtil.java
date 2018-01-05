package com.cherry.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 随机码工具类
 * Created by Administrator on 2017/11/10.
 */
public class KeyUtil {

    /**
     * 生成带时间戳的主键
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000; //生成6位随机数

        return System.currentTimeMillis() + String.valueOf(number);
        // 考虑到多线程同步 还需做synchronized处理
    }

    /**
     * 生成6位随机码
     * @return
     */
    public static synchronized String genRandomCode(){
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000; //生成6位随机数

        return String.valueOf(number);

    }

    /**
     * 对数据进行MD5 32位 小写加密
     * @param initCode
     * @return
     */
    public static synchronized String genMD5Code(String initCode){

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md5.update((initCode).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }


}
