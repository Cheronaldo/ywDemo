package com.cherry.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

/**
 * 手机短信工具类
 * Created by Administrator on 2017/11/08.
 */
public class ShortMessagingServiceUtil {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public int sendsms(String tel) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        int mobile_code = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        NameValuePair[] data = new NameValuePair[]{new NameValuePair("account", "C74015412"), new NameValuePair("password", "b0cdc650c2e5b6fa8d1e9281e77dae55   "), new NameValuePair("mobile", tel), new NameValuePair("content", content)};
        method.setRequestBody(data);

        try {
            client.executeMethod(method);
            String SubmitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);
            if("2".equals(code)) {
                System.out.println("短信提交成功");
            }
            return mobile_code;
        } catch (HttpException var12) {
            var12.printStackTrace();
            return 0;
        } catch (IOException var13) {
            var13.printStackTrace();
            return 0;
        } catch (DocumentException var14) {
            var14.printStackTrace();
            return 0;
        }
    }


}
