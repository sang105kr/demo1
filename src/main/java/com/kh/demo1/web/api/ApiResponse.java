package com.kh.demo1.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Data
public class ApiResponse<T> {
  private Header header;    //응답헤더
  private T body;           //응답바디
  private int totalCnt;     //총건수

  public ApiResponse(Header header, T body, int totalCnt) {
    this.header = header;
    this.body = body;
    totalCnt = totalCnt;
  }

  @Data
  @AllArgsConstructor
  public static class Header{
    private String rtcd;      //응답코드
    private String rtmsg;     //응답메시지

  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body){
    int totalCnt = 0;
    log.info("type={}",body.getClass().getTypeName());
    if(body instanceof Collection<?>){
      totalCnt = ((Collection<?>) body).size();
    }else if(body instanceof Map<?,?>){
      totalCnt = ((Map<?,?>) body).size();
    }else {
      totalCnt = 1;
    }
    return new ApiResponse<>(new Header(rtcd,rtmsg), body, totalCnt);
  }
}
