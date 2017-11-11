package com.cherry.dto;

/**
 * 现场用户查询设备列表（主页面）DTO
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceListDTO {


    /**  设备SN码 */
    private String snCode;
    /** 设备部署地址  */
    private String deviceAddress;
    /**  设备状态码 0 表示离线 1 表示在线 */
    private Integer isOnline;
    /**  现场名称 */
    private String siteName;
    /**  设备类型(注意将类型码转换为 字符串传给前端)*/
    private String deviceType;
    /**  设备型号 */
    private String deviceModel;
    /** 现场图标  */
    private String siteIcon;


    public SiteDeviceListDTO(){}

}
