package com.kh.demo1.domain.svc.pubdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ApiResult {
  private Response response;

  @Getter
  @Setter
  @ToString
  public static class Response {
    private Header header;
    private Body body;

    @Getter
    @Setter
    @ToString
    public static class Header {
      private String resultCode;
      private String resultMsg;
    }

    @Getter
    @Setter
    @ToString
    public static class Body {
      private Items Items;
      private int numOfRows;
      private int pageNo;
      private int totalCount;

      @Getter
      @Setter
      @ToString
      public static class Items {
        private List<Item> item;

        @Getter
        @Setter
        @ToString
        public static class Item {
          private String buildAddress;
          private String buildPlace;
          private String clerkTel;
          private long cnt;
          private double distance;
          private long friEndTme;
          private String friSttTme;
          private long holEndTme;
          private String holSttTme;
          private String manager;
          private String managerTel;
          private String mfg;
          private String model;
          private long monEndTme;
          private String monSttTme;
          private String org;
          private long rnum;
          private long satEndTme;
          private String satSttTme;
          private long sunEndTme;
          private String sunFifYon;
          private String sunFrtYon;
          private String sunFurYon;
          private String sunScdYon;
          private String sunSttTme;
          private String sunThiYon;
          private long thuEndTme;
          private String thuSttTme;
          private long tueEndTme;
          private String tueSttTme;
          private long wedEndTme;
          private String wedSttTme;
          private double wgs84Lat;
          private String wgs84Lon;
          private long zipcode1;
          private long zipcode2;
        }
      }
    }
  }
}



