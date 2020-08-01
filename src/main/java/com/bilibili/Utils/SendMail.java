package com.bilibili.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/*
1。创建会话，设置客户端使用协议，邮箱服务器地址，是否可以授权
2。设置消息内容，发件人，title，文案内容，收件人信息
3。发送消息，账号密码校验发送
 */
public class SendMail {

    public void sendMessage(String[] to,String title,String text) throws IOException, MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.transport.protocol","smtp");
        prop.setProperty("mail.smtp.host","smtp.qq.com");
        prop.setProperty("mail.smtp.port","587");
        Session mailSession = Session.getInstance(prop);//建立会话
        Message msg = new MimeMessage(mailSession);//建立信息

        PropUtil pu = new PropUtil("./src/main/resources/configs/global.properties");
        String sendUser = pu.getProp("sendUser");
        String password = pu.getProp("password");
        msg.setFrom(new InternetAddress(sendUser));

        String toPerson = getMailList(to);
        System.out.println(111);
//        String toPerson = pu.getProp("tomail");
        InternetAddress[] iaToList = new InternetAddress().parse(toPerson);

        msg.setRecipients(Message.RecipientType.TO,iaToList);
        msg.setSentDate(new Date());
        msg.setSubject(title);
        msg.setText(text);

        Transport tran = mailSession.getTransport("smtp");
//        tran.connect("smtp.qq.com",sendUser,password);
        tran.connect("smtp.qq.com",sendUser,password);
        tran.sendMessage(msg,msg.getAllRecipients());//发送
        System.out.println("邮件发送成功");


    }

    private String getMailList(String[] mailArray){
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        System.out.println(222);
        if (mailArray != null && length<2){
            toList.append(mailArray[0]);
        }else {
            for(int i=0;i<length;i++){
                toList.append(mailArray[i]);
                if(i !=(length-1)){
                    toList.append(",");
                }
            }
        }
        return toList.toString();
    }

    public static String getStringDate(){
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(currentTime);
        return format;
    }

    public static void main(String[] args) throws IOException, MessagingException {
        SendMail sendMail = new SendMail();
        PropUtil p = new PropUtil("/Users/tianqingxia/IdeaProjects/frameworkTest/src/main/resources/configs/global.properties");
        String[] tomails = p.getProp("tomail").split(",");
//        sendMail.sendMessage(tomails,"邮件编写学习","工具类编写");
        StringBuilder sb = new StringBuilder();
        sb.append("第一行\n");
        sb.append("第二行\n");
        sb.append("第三行\n");
        sb.append("第四行\n");
        sb.append("\n time "+getStringDate());
        sendMail.sendMessage(tomails,"新邮件",sb.toString());

    }
}
