package com.kh.demo1.common;

import com.kh.demo1.web.api.ApiResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class MyUtil {
  //List를 문자열로 변환
  static public String listToString(List<String> hobbys) {
    String tmp = "";
    for (String hobby : hobbys) {
      tmp += hobby + ",";
    }
    tmp = tmp.substring(0,tmp.length()-1);
    return tmp;
  }

  static public ApiResponse<Object> validChkApiReq(BindingResult bindingResult) {
    ApiResponse<Object> res = null;

    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("errors");    // errors.properties 파일을 찾도록 설정

    StringBuffer errMsg = new StringBuffer();
    for(ObjectError error : bindingResult.getAllErrors()){
      // 필드오류만 추출 : 오류필드명을 알기위함
      if(error instanceof FieldError){
        FieldError fieldError = (FieldError) error;
        String localizedErrMsg = messageSource.getMessage(fieldError , LocaleContextHolder.getLocale());
        errMsg.append(fieldError.getField()).append(":").append(localizedErrMsg).append("; ");  //  필드명:오류메세지1; 필드명:오류메세지2;
      // 글로벌오류  : 오류코드를 알기위함
      }else{
        String localizedErrMsg = messageSource.getMessage(error , LocaleContextHolder.getLocale());
        errMsg.append(error.getCode()).append(":").append(localizedErrMsg).append("; ");  //  오류코드:오류메세지1; 오류코드:오류메세지2;
      }
    }
    res = ApiResponse.createApiResponse("99", errMsg.toString(), null);
    return res;
  }
}
