package com.kh.demo1.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

  //관리자 초기화면
  @GetMapping
  public String intro(){

    return "admin/admin";
  }
}
