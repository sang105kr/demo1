package com.kh.demo1.common;

import java.util.List;

public class MyUtil {
  //List를 문자열로 변환
  static public String listToString(List<String> hobbys) {
    String tmp = "";
    for (String hobby : hobbys) {
      tmp += hobby + ",";
    }
    tmp = tmp.substring(0,tmp.length()-1);
    return tmp;
  }
}
