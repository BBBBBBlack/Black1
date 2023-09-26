package com.example.test01_coll.utils;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailUtil {

    public static Boolean emailSend(String email,String contents)  {

        System.out.println(email);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.qq.com");

        mailSender.setUsername("2582085906@qq.com");

        mailSender.setPassword("cpwxlylqqeohebha");

        mailSender.setPort(465);

        mailSender.setProtocol("smtps");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("2582085906@qq.com");//发送的

        message.setTo(email);

        message.setSubject("【文件管理系统】");

        message.setText(contents);

        try {
            mailSender.send(message);
        }catch (MailException ignored){
            return false;
        }

        return true;
    }
}
