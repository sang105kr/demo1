package com.kh.demo1;

import com.kh.demo1.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //인증 체크
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)                     //인터셉터 실행 순서지정
        .addPathPatterns("/**")       //인터셉터에 포함시키는 url패턴, 루트부터 하위경로 모두
        .excludePathPatterns(
            "/",                      //초기화면
            "/login",                 //login
            "/logout",                //logout
            "/members/add",            //회원가입
            "/css","/js","/img"       //정적리소스
        );  // 인터셉터 제외 url패턴
  }
}
