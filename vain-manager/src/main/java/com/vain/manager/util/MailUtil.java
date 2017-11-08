package com.vain.manager.util;


import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author vain
 * @Descritpion 邮件发送帮助类 仅支持简单邮件
 * @Date 20:54 2017/11/8
 */
public class MailUtil {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 发送邮件工具类:通过qq邮件发送,因为具有ssl加密,采用的是smtp协议
     *
     * @param mailServer       邮件服务器的主机名:如 "smtp.qq.com"
     * @param loginAccount     登录邮箱的账号:如 "xxxxxx@qq.com"
     * @param loginAuthCode    登录qq邮箱时候需要的授权码:可以进入qq邮箱,账号设置那里"生成授权码"
     * @param sender           发件人
     * @param recipients       收件人:支持群发
     * @param emailSubject     邮件的主题
     * @param emailContent     邮件的内容
     * @param emailContentType 邮件内容的类型,支持纯文本:"text/plain;charset=utf-8";,带有Html格式的内容:"text/html;charset=utf-8"
     * @return
     */
    public int sendEmail(String mailServer, final String loginAccount, final String loginAuthCode, String sender, String[] recipients,
                         String emailSubject, String emailContent, String emailContentType) {
        int res = 0;
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        try {
            //跟smtp服务器建立一个连接
            Properties p = new Properties();
            //设置邮件服务器主机名
            p.setProperty("mail.host", mailServer);
            p.setProperty("mail.smtp.port", "465"); //阿里服务器屏蔽了25端口 采用ssl加密的465
            //发送服务器需要身份验证,要采用指定用户名密码的方式去认证
            p.setProperty("mail.smtp.auth", "true");
            //发送邮件协议名称
            p.setProperty("mail.transport.protocol", "smtp");
            p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            p.setProperty("mail.smtp.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.socketFactory.port", "465");

            //开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            Session session = Session.getDefaultInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    //用户名可以用QQ账号也可以用邮箱的别名:第一个参数为邮箱账号,第二个为授权码
                    PasswordAuthentication pa = new PasswordAuthentication(loginAccount, loginAuthCode);
                    return pa;
                }
            });

            //设置打开调试状态
            session.setDebug(true);

            //可以发送几封邮件:可以在这里 for循环多次
            //声明一个Message对象(代表一封邮件),从session中创建
            MimeMessage msg = new MimeMessage(session);
            //邮件信息封装
            //1发件人
            msg.setFrom(new InternetAddress(sender));

            //2收件人:可以多个
            InternetAddress[] receptientsEmail = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                receptientsEmail[i] = new InternetAddress(recipients[i]);
            }

            //多个收件人
            msg.setRecipients(RecipientType.TO, receptientsEmail);

            //3邮件内容:主题、内容
            msg.setSubject(emailSubject);
            //msg.setContent("Hello, 我是debug!!!", );//纯文本
            msg.setContent(emailContent, emailContentType);//发html格式的文本
            //发送动作
            Transport.send(msg);
            logger.info("邮件发送成功");
            res = 1;
        } catch (Exception e) {
            logger.info("邮件发送失败: " + e.getMessage());
            res = 0;
        }
        return res;
    }
}
