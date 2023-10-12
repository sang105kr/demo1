package com.kh.demo1.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //리다이렉트 URL만들기
    String redirectUrl = null;
    //요청 uri   ex) get http://localhost:9080/products 상품관리
    String requestURI = request.getRequestURI();  //   /product
    log.info("requestURI={}", requestURI);

    //요청 파라미터 정보가 있는경우   http://localhost:9080/products?aaa=xxx&bbb=yyy
    if(request.getQueryString() != null){
      //요청 파라미터 인코딩
      String queryString = URLEncoder.encode(request.getQueryString(),"UTF-8");  // aa=xxx&bbb=yyy
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("&").append(queryString).toString(); //   /products?aaa=xxx&bbb=yyy

    }else{
      //요청 파라미터 정보가 없는경우
      redirectUrl = requestURI;    //  /products
    }

    //세션조회
    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("loginMember") == null){
      log.info("미인증 요청");
      //로그인화면
      // 302  http://localhost:9080/login?redirectURL=/products
      response.sendRedirect("/login?redirectUrl=" + redirectUrl);
      return false;  // 다음 인터셉터 포함하여 컨트롤러 수행하지 않음.
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
