/*
package com.szh.util;

import org.apache.commons.mail.Email;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.util.Properties;

*/
/**
 * Created by zhihaosong on 16-8-22.
 *//*

public class SendMail {
    public static void main(String[] args) {
        String charset = "UTF-8";
        Properties prop = new Properties();
        prop.setProperty(Email.MAIL_TRANSPORT_PROTOCOL, Email.SMTP);
        prop.setProperty(Email.MAIL_HOST, "211.151.116.68");
        prop.setProperty(Email.MAIL_PORT,
                String.valueOf(25));
        prop.setProperty("mail.smtp.timeout", "2000");
        prop.setProperty("mail.smtp.connectiontimeout", "2000");
        Session session = Session.getInstance(prop, null);
        MimeMessage message = new MimeMessage(session);
        Transport myTransport = null;
        String error = "";
        try {
            InternetAddress toAddr = new InternetAddress("1551151530@qq.com");
            toAddr.setPersonal("shz", charset);
            message.addRecipient(Message.RecipientType.TO, toAddr);
            message.setHeader("List-Unsubscribe", "http://www.dajie.com/settings/email");

            String from = "";
            if (from == null || "".equals(from)) {
                from = "noreply@sysmail.dajie.com";
            } else {
                from = from.split("@")[0] + "@" + "sysmail.dajie.com";
            }
            InternetAddress fromAddr = new InternetAddress(from);

            String fromName = "大街网";
            fromAddr.setPersonal(fromName, charset);

            message.setFrom(fromAddr);

            String subject = "";
            String content = "";
            String attachmentTitle = "";
            String attachmentUrl = "";
            subject = "nihao,main.java.com.szh.test.test";
            content = "测试邮件";
            if (subject == null || subject.isEmpty() || content == null || content.isEmpty()) {
            }
            message.setSubject(subject, charset);
            if (!attachmentTitle.isEmpty() && !attachmentUrl.isEmpty()) {
                Multipart multipart = new MimeMultipart();
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(content, "text/html;charset=" + charset);
                multipart.addBodyPart(bodyPart);
                MimeBodyPart attachPart = new MimeBodyPart();
                URLDataSource uds = new URLDataSource(new URL(attachmentUrl));
                attachPart.setDataHandler(new DataHandler(uds));
                attachPart.setFileName(MimeUtility.encodeText(attachmentTitle));
                multipart.addBodyPart(attachPart);
                message.setContent(multipart);
                message.saveChanges();
            } else {
                message.setContent(content, "text/html;charset=" + charset);
            }


            myTransport = session.getTransport("smtp");
            myTransport.connect();
            myTransport.sendMessage(message,
                    message.getRecipients(javax.mail.Message.RecipientType.TO));
        } catch (Exception e) {
            error = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (myTransport != null) {
                    myTransport.close();
                }
            } catch (MessagingException mex) {
            }
        }
    }
}
*/
