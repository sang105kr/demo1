package com.kh.demo1.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/service")
public class ServiceController {

  //편의점
  @GetMapping("/cstore")
  public String cstore(){
    return "service/cstore";
  }

  //AED
  @GetMapping("/aed")
  public String aed(){
    return "service/aed";
  }
}
