package com.kh.demo1.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class JsonToJavaTest {
  @Getter
  @Setter
  @NoArgsConstructor  //디폴트생성자
  static class Email {
    private String tel;
  }

  @Test
  @DisplayName("json포맷 문자열 자바객체 변환")
  void jsonToJava() throws JsonProcessingException {
    String str = "{ \"tel\" : \"010-1234-5678\"  }";
    ObjectMapper jsonMapper = new ObjectMapper();
    Email email = jsonMapper.readValue(str, Email.class);
    log.info("email={}", email.getTel());
  }

  @Test
  @DisplayName("자바객체를 json포맷 문자열  변환")
  void javaToJson() throws JsonProcessingException {
    Email email = new Email();
    email.setTel("010-1234-5678");

    ObjectMapper jsonMapper = new ObjectMapper();
    String result = jsonMapper.writeValueAsString(email);
    log.info("result={}", result);
  }
}
