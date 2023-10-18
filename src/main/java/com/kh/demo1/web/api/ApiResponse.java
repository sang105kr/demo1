package com.kh.demo1.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private Header header;    //응답헤더
  private T body;           //응답바디

//  public ApiResponse(Header header, T body) {
//    this.header = header;
//    this.body = body;
//  }

  @Data
  @AllArgsConstructor
  public static class Header{
    private String rtcd;      //응답코드
    private String rtmsg;     //응답메시지

//    public Header(String rtcd, String rtmsg) {
//      this.rtcd = rtcd;
//      this.rtmsg = rtmsg;
//    }
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body){
    return new ApiResponse<>(new Header(rtcd,rtmsg), body);
  }
}
