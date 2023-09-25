package com.kh.demo1.web;

import com.kh.demo1.web.form.SaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/products")   // http://localhost:9080/products
public class ProductController {

  //등록양식
  @GetMapping("/add")         // GET http://localhost:9080/products/add
  public String addForm(){
    log.info("addForm호출됨!");
    return "product/add";     // view 이름
  }

  //등록처리
  @PostMapping("/add")       // POST http://localhost:9080/products/add
  public String add(
//      @RequestParam("pname") String pname,
//      @RequestParam("quantity") String quantity,
//      @RequestParam("price") String price
      SaveForm saveForm, RedirectAttributes redirectAttributes
  ){
    log.info("add호출됨!={}",saveForm);
//    log.info("add호출됨!={},{},{}",pname,quantity,price);
    // 요청데이터 유효성 체크
    // 상품등록

    redirectAttributes.addAttribute("id", 1);
    return "redirect:/products/{id}/detail";   // GET http://localhost:9080/products/1/detail
  }

  //조회
  @GetMapping("/{id}/detail")  //GET http://localhost:9080/products/1/detail
  public String findById(@PathVariable("id") Long id){
    //상품조회

    return "product/detailForm";
  }

}
