package com.cherry.util;

import com.cherry.constant.MailConstant;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件处理工具类
 * Created by Administrator on 2018/01/05.
 */
public class MailUtil {


    /**
     * 现场用户注册 发送用户名及初始密码
     * @param mail
     * @param userName
     * @param password
     * @return
     */
    public static Integer sendMail(String mail, String userName, String password){

        try {

            //这里使用的是qq邮箱发送
            String email = MailConstant.FROM_EMAIL;
            String pwd = MailConstant.MAIL_PASSWORD;//非QQ邮箱密码；是qq邮箱安全码

            String toEmail = mail;//接收的邮箱

            Properties props = new Properties();


            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");

            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");



            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);
            session.setDebug(true);
            Message msg = new MimeMessage(session);
            msg.setSubject("亿维自动化用户注册");
            StringBuilder builder = new StringBuilder();
            builder.append("\n用户名：" + userName );
            builder.append("\n初始密码：" + password);
            builder.append("\n注册时间: " + DateUtil.convertDate2String(new Date()));
//            builder.append("\n请您尽快登录并修改密码！");
            msg.setText(builder.toString());
            msg.setFrom(new InternetAddress(email));//"**发送人的邮箱地址**"

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", email, pwd);

            transport.sendMessage(msg, new Address[] { new InternetAddress(toEmail) });//"**接收人的邮箱地址**"
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;

    }

}
