package com.kh.demo1.web;

import com.kh.demo1.common.MyUtil;
import com.kh.demo1.domain.svc.pubdata.AedSVC;
import com.kh.demo1.web.api.ApiResponse;
import com.kh.demo1.web.req.pubdata.AedData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pubdata")
public class ApiPubDataController {
  private final AedSVC aedSVC;

  // 원본 데이터 10건
  @GetMapping("/aed")
  public String rawAed(@RequestBody AedData aedData){
    log.info("aed호출됨={}",aedData);
    String data = aedSVC.requestAde(aedData.getLat(), aedData.getLng());
    return data;
  }

  // 원본 데이터(페이지,건수)
  @GetMapping("/aed2")
  public String rawAed2(@RequestBody AedData aedData){
    log.info("aed2호출됨={}",aedData);
    String data = aedSVC.requestAde(aedData.getLat(), aedData.getLng(), aedData.getPageNo(), aedData.getNumOfRows());
    return data;
  }
  
  // 필더된 데이터 10건
  @GetMapping("/aed3")
  public ApiResponse<Object> filtedAed(@Valid AedData aedData, BindingResult bindingResult){
    ApiResponse<Object> res = null;

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return MyUtil.validChkApiReq(bindingResult);
    }

    log.info("aed3호출됨={}",aedData);
    Object data = aedSVC.requestAdeFilter(aedData.getLat(), aedData.getLng(), aedData.getPageNo(), aedData.getNumOfRows());
    if(data != null){
      res = ApiResponse.createApiResponse("00","success",data);
    }else{
      res = ApiResponse.createApiResponse("99","fail",null);
    }
    return res;
  }
}
