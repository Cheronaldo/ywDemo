package com.cherry.util;

import com.cherry.dto.ExcelDataDTO;

/**
 * 字段添加 测试工具类
 * Created by Administrator on 2018/01/24.
 */
public class PreCompileProcessUtil {

    public static void main(String[] args) {

//        // 为 Student 添加字段
        AddFieldUtil add = new AddFieldUtil(ExcelDataDTO.class);

        // 添加一个名为 address，类型为 java.lang.String 的 public 字段
        add.addPublicField("data30", "Ljava/lang/String;");
        add.addPublicField("data29", "Ljava/lang/String;");
        add.addPublicField("data28", "Ljava/lang/String;");
        add.addPublicField("data27", "Ljava/lang/String;");
        add.addPublicField("data26", "Ljava/lang/String;");
        add.addPublicField("data25", "Ljava/lang/String;");
        add.addPublicField("data24", "Ljava/lang/String;");
        add.addPublicField("data23", "Ljava/lang/String;");
        add.addPublicField("data22", "Ljava/lang/String;");
        add.addPublicField("data21", "Ljava/lang/String;");
        add.addPublicField("data20", "Ljava/lang/String;");
        add.addPublicField("data19", "Ljava/lang/String;");
        add.addPublicField("data18", "Ljava/lang/String;");
        add.addPublicField("data17", "Ljava/lang/String;");
        add.addPublicField("data16", "Ljava/lang/String;");
        add.addPublicField("data15", "Ljava/lang/String;");
        add.addPublicField("data14", "Ljava/lang/String;");
        add.addPublicField("data13", "Ljava/lang/String;");
        add.addPublicField("data12", "Ljava/lang/String;");
        add.addPublicField("data11", "Ljava/lang/String;");
        add.addPublicField("data10", "Ljava/lang/String;");
        add.addPublicField("data9", "Ljava/lang/String;");
        add.addPublicField("data8", "Ljava/lang/String;");
        add.addPublicField("data7", "Ljava/lang/String;");
        add.addPublicField("data6", "Ljava/lang/String;");
        add.addPublicField("data5", "Ljava/lang/String;");
        add.addPublicField("data4", "Ljava/lang/String;");
        add.addPublicField("data3", "Ljava/lang/String;");
        add.addPublicField("data2", "Ljava/lang/String;");
        add.addPublicField("data1", "Ljava/lang/String;");

        // 重新生成 .class 文件
        add.writeByteCode();

    }


}
