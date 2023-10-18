package com.kh.demo1.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private Header header;    //응답헤더
  private T body;           //응답바디
  private int totalCnt;     //총건수

  @Data
  @AllArgsConstructor
  public static class Header{
    private String rtcd;      //응답코드
    private String rtmsg;     //응답메시지
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body){
    int totalCnt = 0;

    if(ClassUtils.isAssignable(Collection.class, body.getClass())){
      totalCnt = ((Collection<?>) body).size();
    }else if(ClassUtils.isAssignable(Map.class, body.getClass())){
      totalCnt = ((Map<?,?>) body).size();
    }else {
      totalCnt = 1;
    }
    return new ApiResponse<>(new Header(rtcd,rtmsg), body, totalCnt);
  }
}
