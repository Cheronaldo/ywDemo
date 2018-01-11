package com.cherry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目url配置类
 * Created by Administrator on 2018/01/11.
 */
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    /**
     * 异常跳转url
     */
    public String ywDemo;

    public String getYwDemo() {
        return ywDemo;
    }

    public void setYwDemo(String ywDemo) {
        this.ywDemo = ywDemo;
    }
}
