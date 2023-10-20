package com.kh.demo1.domain.svc.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AedSVCImpl implements AedSVC{

  private final WebClient webClient;
  private final String baseUrl = "http://apis.data.go.kr";
  private final String serviceKey = "CryKKi6HaVVnP0WXU4sIp8dcrZgn2wui0UPEU%2BeivronhsULZ8SFW3qxmqgGmyqgpj59gqzMmd8H%2BhWEzjcvBw%3D%3D";

  @Autowired
  public AedSVCImpl(WebClient.Builder webClientBilder) {

    //쿼리파라미터 인코딩 막기 (왜? 공공데이터 service키가 인코딩된 값이므로 불필요)
    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    this.webClient = webClientBilder
        .uriBuilderFactory(factory)
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)  //json포맷으로 요청
        .build();
  }

  @Override
  public String requestAde(String lat, String lng) {
    final String pageNo = "1";
    final String numOfRows = "10";

    Mono<String> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", lng)    //경도   129.3076
            .queryParam("WGS84_LAT", lat)    //위도    35.53235
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", numOfRows)
            .build())
        .retrieve()
        .bodyToMono(String.class);
    String data = response.block();
    return data;
  }

  @Override
  public String requestAde(String lat, String lng, String pageNo, String numOfRows) {

    Mono<String> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", lng)    //경도
            .queryParam("WGS84_LAT", lat)    //위도
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", numOfRows)
            .build())
        .retrieve()
        .bodyToMono(String.class);
    String data = response.block();
    return data;
  }

  @Override
  public Object requestAdeFilter(String lat, String lng) {
    final String pageNo = "1";
    final String numOfRows = "10";

    Mono<ApiResult> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", lng)    //경도   129.3076
            .queryParam("WGS84_LAT", lat)    //위도    35.53235
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", numOfRows)
            .build())
        .retrieve()
        .bodyToMono(ApiResult.class);
    ApiResult data = response.block();
    Object items = data.getResponse().getBody().getItems();
    return items;
  }

  @Override
  public Object requestAdeFilter(String lat, String lng, String pageNo, String numOfRows) {
    Mono<ApiResult> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/B552657/AEDInfoInqireService/getAedLcinfoInqire")
            .queryParam("serviceKey", serviceKey)
            .queryParam("WGS84_LON", lng)    //경도   129.3076
            .queryParam("WGS84_LAT", lat)    //위도    35.53235
            .queryParam("pageNo", pageNo)
            .queryParam("numOfRows", numOfRows)
            .build())
        .retrieve()
        .bodyToMono(ApiResult.class);
    ApiResult data = response.block();
    Object items = data.getResponse().getBody().getItems();
    return items;
  }

}
