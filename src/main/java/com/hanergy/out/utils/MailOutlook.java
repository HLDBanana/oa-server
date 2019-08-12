package com.hanergy.out.utils;


import com.hanergy.out.vo.MeetingParamVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName MailOutlook
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/13 11:46)
 */
@Component
@PropertySource("classpath:config/outlook.properties")
public class MailOutlook {

    Logger log = LoggerFactory.getLogger(MailOutlook.class);

    @Value("${outlook.sender.username}")
    private String username;

    @Value("${outlook.sender.password}")
    private String pwd;

    @Value("${outlook.host}")
    private String host;

    @Value("${outlook.port}")
    private String port;

    private Properties emailProp = new Properties();
    private class EmailAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            String userId = emailProp.getProperty("userId", username);
            String password = emailProp.getProperty("password", pwd);
            return new PasswordAuthentication(userId, password);
        }
    }

    //发送会议预约邮件
    public void send(MeetingParamVo meeting,String uuid) {
        try {
            //需要转换的时间
            String startTime = getUtc(meeting.getBeginTime());
            String endTime = getUtc(meeting.getEndTime());

            String fromEmail = emailProp.getProperty("fromEmail", username);
            String toEmail=emailProp.getProperty("toEmail", "hanlidong@hanergy.com");
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", port);
                //
                props.put("mail.smtp.host", host);
                //props.put("mail.smtp.host", " smtp.163.com");
                //props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.transport.protocol", "smtp");
                //props.put("mail.smtp.auth", "true");      配置这三条会报错
                //props.put("mail.smtp.ssl.enable", "true");
                //props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.ssl", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));        //发件人
            //设置多发件人    BCC：抄送  CC：密送  TO：收件人
            if (meeting.getEmailList() != null && meeting.getEmailList().size() > 0){
                message.addRecipients(Message.RecipientType.TO,setRecipientT0(meeting.getEmailList()));
            }
            //设置单个发件人
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //设置抄送地址    BCC：抄送  CC：密送  TO：收件人
            if (meeting.getCcEmailList() != null && meeting.getCcEmailList().size() > 0){
                message.addRecipients(Message.RecipientType.BCC,setRecipientT0(meeting.getCcEmailList()));
            }
            message.setSubject(meeting.getTopic());         //设置会议主题
            StringBuffer buffer = new StringBuffer();
            buffer.append("BEGIN:VCALENDAR\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "VERSION:2.0\n"
                    + "METHOD:REQUEST\n"        //METHOD:CANCEL 取消会议  METHOD:REQUEST 创建和更新会议
                    + "BEGIN:VEVENT\n"
                    + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+fromEmail+"\n"
                    + "ORGANIZER:MAILTO:"+fromEmail+"\n"
                    + "DTSTART:"+startTime+"\n"
                    + "DTEND:"+endTime+"\n"
                    + "LOCATION:"+meeting.getLocation() +"\n"
                    //+ "LOCATION:会议室01\n"
                    //如果id相同的话，outlook会认为是同一个会议请求，所以使用uuid。
                    //+ "UID:"+ UUID.randomUUID().toString()+"\n"
                    + "UID:"+ uuid +"\n"
                    + "CATEGORIES:会议邀请\n"
                    + "DESCRIPTION:"+meeting.getContent()+"\n\n"        //设置会议内容
                    + "SUMMARY:2222222222t\n" + "PRIORITY:5\n"
                    + "SEQUENCE:1\n"        //SEQUENCE:1 设置优先级 cance>update>create
                    + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
                    + "TRIGGER:-PT15M\n" + "ACTION:DISPLAY\n"
                    + "DESCRIPTION:Reminder\n" + "END:VALARM\n"
                    + "END:VEVENT\n" + "END:VCALENDAR");
            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去， text/calendar
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=REQUEST;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart,"text/html;charset=UTF-8");
            //message.setContent(multipart);
            //message.setText("测试");
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //取消会议预约
    public void cancel(MeetingParamVo meeting) {
        try {
            //需要转换的时间
            String startTime = getUtc(meeting.getBeginTime());
            String endTime = getUtc(meeting.getEndTime());

            String fromEmail = emailProp.getProperty("fromEmail", username);
            String toEmail=emailProp.getProperty("toEmail", "hanlidong@hanergy.com");
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", port);
                //
                props.put("mail.smtp.host", host);
                //props.put("mail.smtp.host", " smtp.163.com");
                //props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.transport.protocol", "smtp");
                //props.put("mail.smtp.auth", "true");      配置这三条会报错
                //props.put("mail.smtp.ssl.enable", "true");
                //props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.ssl", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));        //发件人
            //设置多发件人
            if (meeting.getEmailList() != null && meeting.getEmailList().size() > 0) {
                message.addRecipients(Message.RecipientType.TO,setRecipientT0(meeting.getEmailList()));
            }

            //设置单个发件人
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //设置抄送地址 Message.RecipientType.CC 密送
            if (meeting.getCcEmailList() != null && meeting.getCcEmailList().size() > 0) {
                message.addRecipients(Message.RecipientType.BCC,setRecipientT0(meeting.getCcEmailList()));
            }

            message.setSubject(meeting.getTopic());         //设置会议主题
            StringBuffer buffer = new StringBuffer();
            buffer.append("BEGIN:VCALENDAR\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "VERSION:2.0\n"
                    + "METHOD:CANCEL\n"        //METHOD:CANCEL 取消会议  METHOD:REQUEST 创建和更新会议
                    + "BEGIN:VEVENT\n"
                    + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+fromEmail+"\n"
                    + "ORGANIZER:MAILTO:"+fromEmail+"\n"
                    + "DTSTART:"+startTime+"\n"
                    + "DTEND:"+endTime+"\n"
                    + "LOCATION:"+meeting.getLocation() +"\n"
                    //+ "LOCATION:会议室01\n\n"
                    //如果id相同的话，outlook会认为是同一个会议请求，所以使用uuid。
                    //+ "UID:"+ UUID.randomUUID().toString()+"\n"
                    + "UID:"+ meeting.getUuid() +"\n"
                    + "CATEGORIES:会议取消\n"
                    + "DESCRIPTION:由于特殊原因，会议取消\n"        //设置会议内容
                    + "SUMMARY:2222222222t\n" + "PRIORITY:5\n"
                    + "SEQUENCE:3\n"        //SEQUENCE:1 设置优先级 cance>update>create
                    + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
                    + "TRIGGER:-PT15M\n" + "ACTION:DISPLAY\n"
                    + "DESCRIPTION:Reminder\n" + "END:VALARM\n"
                    + "END:VEVENT\n" + "END:VCALENDAR");
            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去，
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=CANCEL;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            //message.setText("测试");
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //取消会议预约
    public void update(MeetingParamVo meeting) {
        try {
            //需要转换的时间
            String startTime = getUtc(meeting.getBeginTime());
            String endTime = getUtc(meeting.getEndTime());

            String fromEmail = emailProp.getProperty("fromEmail", username);
            String toEmail=emailProp.getProperty("toEmail", "hanlidong@hanergy.com");
            Properties props = new Properties();
            try {
                props.put("mail.smtp.port", port);
                props.put("mail.smtp.host", host);
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.ssl", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Session session;
            Authenticator authenticator = new EmailAuthenticator();
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));        //发件人
            //设置多发件人
            if (meeting.getEmailList() != null && meeting.getEmailList().size() > 0) {
                message.addRecipients(Message.RecipientType.TO, setRecipientT0(meeting.getEmailList()));
            }
            //设置单个发件人
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //设置抄送地址 Message.RecipientType.CC 密送
            if (meeting.getCcEmailList() != null && meeting.getCcEmailList().size() > 0) {
                message.addRecipients(Message.RecipientType.BCC, setRecipientT0(meeting.getCcEmailList()));
            }
            message.setSubject(meeting.getTopic());         //设置会议主题
            StringBuffer buffer = new StringBuffer();
            buffer.append("BEGIN:VCALENDAR\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "VERSION:2.0\n"
                    + "METHOD:REQUEST\n"        //METHOD:CANCEL 取消会议  METHOD:REQUEST 创建和更新会议
                    + "BEGIN:VEVENT\n"
                    + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+fromEmail+"\n"
                    + "ORGANIZER:MAILTO:"+fromEmail+"\n"
                    + "DTSTART:"+startTime+"\n"
                    + "DTEND:"+endTime+"\n"
                    + "LOCATION:"+meeting.getLocation() +"\n"
                    //+ "LOCATION:会议室01\n\n"
                    //如果id相同的话，outlook会认为是同一个会议请求，所以使用uuid。
                    //+ "UID:"+ UUID.randomUUID().toString()+"\n"
                    + "UID:"+ meeting.getUuid() +"\n"
                    + "CATEGORIES:会议变更\n"
                    + "DESCRIPTION:"+meeting.getContent()+"\n\n"       //设置会议内容
                    + "SUMMARY:2222222222t\n" + "PRIORITY:5\n"
                    + "SEQUENCE:2\n"        //SEQUENCE:1 设置优先级 cance>update>create
                    + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
                    + "TRIGGER:-PT15M\n" + "ACTION:DISPLAY\n"
                    + "DESCRIPTION:Reminder\n" + "END:VALARM\n"
                    + "END:VEVENT\n" + "END:VCALENDAR");
            BodyPart messageBodyPart = new MimeBodyPart();
            // 测试下来如果不这么转换的话，会以纯文本的形式发送过去，
            //如果没有method=REQUEST;charset=\"UTF-8\"，outlook会议附件的形式存在，而不是直接打开就是一个会议请求
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=CANCEL;charset=\"UTF-8\"")));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            //message.setText("测试");
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //转utc时间
    public String getUtc(String str){
        //String str = "2015-10-22 15:05";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(str).getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }//毫秒

        long currentTime = millionSeconds - 8*60 * 60 * 1000;//utc时间差8小时
        Date date = new Date(millionSeconds);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化日期
        String nowTime="";
        nowTime= df.format(date);

        String utcTime = nowTime.replace("-", "").replace(" ", "T").replace(":", "");//转换utc时间
        return utcTime;
    }


    /*** 设置收件人/抄送人/密送人地址信息*/
    private InternetAddress[] setRecipientT0(List<String> recipientT0List) throws MessagingException, UnsupportedEncodingException {
        if (recipientT0List.size() > 0) {
            InternetAddress[] sendTo = new InternetAddress[recipientT0List.size()];
            for (int i = 0; i < recipientT0List.size(); i++) {
                System.out.println("发送到:" + recipientT0List.get(i));
                sendTo[i] = new InternetAddress(recipientT0List.get(i), "", "UTF-8");
            }
            return sendTo;
        }
        return null;
    }



}
