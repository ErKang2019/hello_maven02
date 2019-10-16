package com.gome.bean;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class Test {
    @org.junit.Test
    public void test01() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gomeplus.com");
//        email.setSmtpPort(25);
        // 用户名和密码为邮箱的账号和密码（不需要进行base64编码）
        email.setAuthenticator(new DefaultAuthenticator("liyunlong@gome.com.cn", "1234.gome"));

        email.setSSLOnConnect(true);
        email.setFrom("liyunlong@gome.com.cn");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("liyunlong@gome.com.cn");
        email.send();
    }
}
