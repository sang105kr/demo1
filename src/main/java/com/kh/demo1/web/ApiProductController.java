package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Product;
import com.kh.demo1.domain.svc.ProductSVC;
import com.kh.demo1.web.api.ApiResponse;
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
  public ApiResponse<List<Product>> all(){
    ApiResponse<List<Product>> res = null;
    List<Product> products = productSVC.findAll();
    if(products.size() == 0){
      res = ApiResponse.createApiResponse("01", "한건의 목록도 존재하지 않습니다.", null);
    }else{
      res = ApiResponse.createApiResponse("00", "성공", products);
    }
    return res;
  }
}
