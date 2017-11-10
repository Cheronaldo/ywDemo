package com.cherry.dto;

/**
 * 现场用户查询设备列表（地图）DTO
 * Created by Administrator on 2017/11/10.
 */
public class SiteDeviceMapDTO {

    /**  设备SN码 */
    private String snCode;
    /**  设备类型码 */
    private Integer deviceType;
    /**  设备型号 */
    private String deviceModel;
    /**  设备状态码 0 表示离线 1 表示在线 */
    private Integer isOnline;
    /** 设备部署地址  */
    private String deviceAddress;
    /**  设备经度 */
    private String deviceLongitude;
    /**  设备纬度 */
    private String deviceLatitude;
    /** 现场图标  */
    private String siteIcon;

    public SiteDeviceMapDTO(){}

}
