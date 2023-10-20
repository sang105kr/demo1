package com.kh.demo1.domain.svc.api;

import com.kh.demo1.domain.svc.pubdata.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

@Slf4j
public class AedClient {

  @Test
  @DisplayName("공공데이터 받아오기 json")
  void test() throws UnsupportedEncodingException {
    String baseUrl = "http://apis.data.go.kr";
    String serviceKey = "CryKKi6HaVVnP0WXU4sIp8dcrZgn2wui0UPEU%2BeivronhsULZ8SFW3qxmqgGmyqgpj59gqzMmd8H%2BhWEzjcvBw%3D%3D";

    //쿼리파라미터 인코딩 막기 (왜? 공공데이터 service키가 인코딩된 값이므로 불필요)
    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    WebClient webClient = WebClient.builder()
        .uriBuilderFactory(factory)
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)  //json포맷으로 요청
        .build();

    Mono<String> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", 129.3076)    //경도
            .queryParam("WGS84_LAT", 35.53235)    //위도
            .queryParam("pageNo", 1)
            .queryParam("numOfRows", 10)
            .build())
        .retrieve()
        .bodyToMono(String.class);
    String data = response.block(); //동기
    log.info(data);
  }


  @Test
  @DisplayName("json 자바객체에 저장하기")
  void test2() throws UnsupportedEncodingException {
    String baseUrl = "http://apis.data.go.kr";
    String serviceKey = "CryKKi6HaVVnP0WXU4sIp8dcrZgn2wui0UPEU%2BeivronhsULZ8SFW3qxmqgGmyqgpj59gqzMmd8H%2BhWEzjcvBw%3D%3D";

    //쿼리파라미터 인코딩 막기 (왜? 공공데이터 service키가 인코딩된 값이므로 불필요)
    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    WebClient webClient = WebClient.builder()
        .uriBuilderFactory(factory)
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)  //json포맷으로 요청
        .build();

    Mono<ApiResult> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", 129.3076)    //경도
            .queryParam("WGS84_LAT", 35.53235)    //위도
            .queryParam("pageNo", 1)
            .queryParam("numOfRows", 10)
            .build())
        .retrieve()
        .bodyToMono(ApiResult.class);  // json 포맷 문자열 => java객체로 매핑 변환
    ApiResult result = response.block();
    log.info("result={}",result);

    ApiResult.Response.Body.Items items = result.getResponse().getBody().getItems();
    log.info("items={}",items);
  }
}
