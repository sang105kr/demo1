package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ListToString {

  @Test
  void test(){
    List<String> hobbys = new ArrayList<>();
    hobbys.add("수영");
    hobbys.add("등산");
    hobbys.add("골프");

    String tmp = listToString(hobbys);
    log.info("tmp={}", tmp);
  }

  private String listToString(List<String> hobbys) {
    String tmp = "";
    for (String hobby : hobbys) {
      tmp += hobby + ",";
    }
    tmp = tmp.substring(0,tmp.length()-1);
    return tmp;
  }
}
