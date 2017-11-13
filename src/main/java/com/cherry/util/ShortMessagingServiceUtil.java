package com.cherry.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机短信工具类
 * Created by Administrator on 2017/11/08.
 */
@Slf4j
public class ShortMessagingServiceUtil {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public Map<String, Object> sendShortMessage(String telephone) {

        Map<String, Object> map = new HashMap<String, Object>();

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        int mobileCode = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
        String content = new String("您的验证码是：" + mobileCode + "。请不要把验证码泄露给其他人。");
        NameValuePair[] data = new NameValuePair[]{new NameValuePair("account", "C74015412"), new NameValuePair("password", "b0cdc650c2e5b6fa8d1e9281e77dae55   "), new NameValuePair("mobile", telephone), new NameValuePair("content", content)};
        method.setRequestBody(data);

        try {
            client.executeMethod(method);
            String submitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(submitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsId = root.elementText("smsid");
            log.info("code:" + code);
            log.info("msg:" + msg);
            log.info("smsId:" + smsId);
            if ("2".equals(code)) {
                map.put("code", 0);
                map.put("data", mobileCode);
                log.info("短信提交成功");
            }
            if ("4085".equals(code)){
                map.put("code", 1);
                log.info("请求过于频繁");
            }
        } catch (HttpException var12) {
            var12.printStackTrace();
            map.put("code",2);
            log.info("短信发送失败");
        } catch (IOException var13) {
            var13.printStackTrace();
            map.put("code",2);
            log.info("短信发送失败");
        } catch (DocumentException var14) {
            var14.printStackTrace();
            map.put("code", 2);
            log.info("短信发送失败");
        }
        return map;
    }


}
