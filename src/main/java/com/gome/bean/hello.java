package com.gome.bean;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class hello {
    

	public void test01() throws EmailException {
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("");
        Email email = new SimpleEmail();
        email.setHostName("smtp.gomeplus.com");
        email.setSmtpPort(110);
        // 用户名和密码为邮箱的账号和密码（不需要进行base64编码）
        email.setAuthenticator(new DefaultAuthenticator("liyunlong@gome.com.cn", "1234.gome"));

        email.setSSLOnConnect(true);
        email.setFrom("liyunlong@gome.com.cn");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("liyunlong@gome.com.cn");
        email.send();
	}
	
	public String test02() {
        return "hello_maven02，第二次上传";
    }
}
