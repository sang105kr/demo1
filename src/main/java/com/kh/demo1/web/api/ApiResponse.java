package com.kh.demo1.web.api;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
@ToString
public class ApiResponse<T> {
  private Header header;    //응답헤더
  private T body;           //응답바디
  private int totalCnt;     //총건수

  private ApiResponse(Header header, T body, int totalCnt) {
    this.header = header;
    this.body = body;
    this.totalCnt = totalCnt;
  }

  @Getter
  @ToString
  private static class Header{
    private String rtcd;      //응답코드
    private String rtmsg;     //응답메시지

    Header(String rtcd, String rtmsg) {
      this.rtcd = rtcd;
      this.rtmsg = rtmsg;
    }
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body){
    int totalCnt = 0;

    if(body != null) {
      // 바디가 collection계열인지 요소갯수 가져오기
      if (ClassUtils.isAssignable(Collection.class, body.getClass())) {
        totalCnt = ((Collection<?>) body).size();
      // 바디가 Map계열인지 체크하여 요소갯수 가져오기
      } else if (ClassUtils.isAssignable(Map.class, body.getClass())) {
        totalCnt = ((Map<?, ?>) body).size();
      } else {
        totalCnt = 1;
      }
    }
    return new ApiResponse<>(new Header(rtcd,rtmsg), body, totalCnt);
  }
}
