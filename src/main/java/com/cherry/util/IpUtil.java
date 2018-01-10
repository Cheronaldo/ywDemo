package com.cherry.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Ip地址处理工具类
 * Created by Administrator on 2018/01/10.
 */
public class IpUtil {

    /**
     * 反代理解析 获取真实IP
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");

        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();

    }

}
