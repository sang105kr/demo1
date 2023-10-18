package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Product;
import com.kh.demo1.domain.svc.ProductSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequestMapping("/api/products")
//@RestController    // @Controller + @Response
@Controller
@RequiredArgsConstructor
public class ApiProductController {

  private final ProductSVC productSVC;

  //초기화면
  @GetMapping
  public String init(){
    return "/api/product/init";
  }

  //목록
  @ResponseBody
  @GetMapping("/all")
  public List<Product> all(){

    List<Product> products = productSVC.findAll();

    return products;
  }
}
