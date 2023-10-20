package com.kh.demo1.domain.svc.pubdata;

public interface AedSVC {

  // row데이터
  String requestAde(String lat, String lng);

  String requestAde(String lat, String lng, String pageNo,String numOfRows);

  //가공데이터
  Object requestAdeFilter(String lat, String lng);
  Object requestAdeFilter(String lat, String lng, String pageNo,String numOfRows);
}
