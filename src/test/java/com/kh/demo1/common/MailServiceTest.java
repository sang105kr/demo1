package com.kh.demo1.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {
  
  @Autowired
  private MailService mailService;
  
  @Test
  void sendSimpleMail(){
    StringBuffer str = new StringBuffer();
    str.append("<html>");
    str.append("<p><b>이메일 인증번호</b></p>");
    str.append("</html>");
    mailService.sendMail("sang105kr@gmail.com","이메일인증",str.toString());
  }
}
