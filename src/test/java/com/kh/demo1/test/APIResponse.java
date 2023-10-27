package com.kh.demo1.test;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor

public class APIResponse<T> {
  @NotNull
  private String status;
  @NotNull
  private String message;
  private T body;
  private int cnt;
  private String successCode =
      "$statementsTitle.innerText = ajaxResult['message'];" +
          "$statementsTitle.classList.remove(\"failMsg\");" +
          "$statementsTitle.classList.add(\"successMsg\");";
  private String failCode =
      "$statementsTitle.innerText = ajaxResult['message'];" +
          "$statementsTitle.classList.remove(\"successMsg\");" +
          "$statementsTitle.classList.add(\"failMsg\");";

  public APIResponse(T body) {
    this.body = body;
    int totalCnt;
    if (body != null) {
      if (ClassUtils.isAssignable(Collection.class, body.getClass())) {
        totalCnt = ((Collection<?>) body).size();
      } else if (ClassUtils.isAssignable(Map.class, body.getClass())) {
        totalCnt = ((Map<?, ?>) body).size();
      } else {
        totalCnt = 1;
      }
      this.cnt = totalCnt;
    }

  }

  private APIResponse(String status, String msg, T body, int cnt) {
    this.status = status;
    this.message = msg;
    this.body = body;
    this.cnt = cnt;
  }


  public APIResponse<T> createBody(T body) {
    int totalCnt;
    if (body != null) {
      if (ClassUtils.isAssignable(Collection.class, body.getClass())) {
        totalCnt = ((Collection<?>) body).size();
      } else if (ClassUtils.isAssignable(Map.class, body.getClass())) {
        totalCnt = ((Map<?, ?>) body).size();
      } else {
        totalCnt = 1;
      }
    }
    return new APIResponse<T>(body);
  }

  public APIResponse<T> isSuccess() {
    this.status = "success";
    return this;
  }

  public APIResponse<T> isFail() {
    this.status = "fail";
    return this;
  }

  public APIResponse<T> setMessage(String str) {
    this.message = str;
    return this;
  }

  @Override
  public String toString() {
    return "상태 : " + status + ", 바디 : " + body.toString() + ", 응답 개수 : " + cnt;
  }
}