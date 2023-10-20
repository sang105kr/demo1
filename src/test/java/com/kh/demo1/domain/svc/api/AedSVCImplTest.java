package com.kh.demo1.domain.svc.api;

import com.kh.demo1.domain.svc.pubdata.AedSVC;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AedSVCImplTest {

  @Autowired
  private AedSVC aedSVC;

  private String lat = "35.53235";    //위도
  private String lng = "129.3076";    //경도

  @Test
  void requestAde() {
    String data = aedSVC.requestAde(lat, lng);
    log.info(data);
  }

  @Test
  void testRequestAde() {
  }
}