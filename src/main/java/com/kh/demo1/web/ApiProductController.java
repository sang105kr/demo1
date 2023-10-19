package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Product;
import com.kh.demo1.domain.svc.ProductSVC;
import com.kh.demo1.web.api.ApiResponse;
import com.kh.demo1.web.req.product.ReqSave;
import com.kh.demo1.web.req.product.ReqUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

  //등록
  @ResponseBody
  @PostMapping                        // post http://localhost:9080/api/products
  public ApiResponse<Product> add(
      @RequestBody  //요청메세지 바디를 직접 읽음
      ReqSave reqSave){
    log.info("reqSave={}",reqSave);
    ApiResponse<Product> res = null;

    Product product = new Product();
    BeanUtils.copyProperties(reqSave, product); // 객체간 속성 복사

    //등록
    Long productId = productSVC.save(product);

    //응답메시지 바디
    Optional<Product> optionalProduct = productSVC.findById(productId);
    Product savedProduct = optionalProduct.get();
    res = ApiResponse.createApiResponse("00", "success", savedProduct);

    return res;
  }

  //조회
  @ResponseBody
  @GetMapping("/{pid}")               // get http://localhost:9080/api/products/123
  public ApiResponse<Product> findById(@PathVariable("pid") Long pid){
    ApiResponse<Product>  res = null;
    Optional<Product> optionalProduct = productSVC.findById(pid);

    Product findedProduct = null;
    if(optionalProduct.isPresent()){
      findedProduct = optionalProduct.get();
      res = ApiResponse.createApiResponse("00", "success", findedProduct);
    }else{
      res = ApiResponse.createApiResponse("01", "not found", null);
    }
    return res;
  }

  //수정
  @ResponseBody
  @PatchMapping("/{pid}")         // patch http://localhost:9080/api/products/123
  public ApiResponse<Product> update(
      @PathVariable Long pid,
      @RequestBody ReqUpdate reqUpdate){
    log.info("reqUpdate={}",reqUpdate);
    ApiResponse<Product> res = null;

    Product product = new Product();
    BeanUtils.copyProperties(reqUpdate, product);
    int row = productSVC.updateById(pid, product);

    if(row == 1){
      Product findedProduct = productSVC.findById(pid).get();
      res = ApiResponse.createApiResponse("00","success",findedProduct);
    }else{
      res = ApiResponse.createApiResponse("99","fail",null);
    }

    return res;
  }

  //삭제
  @ResponseBody
  @DeleteMapping("/{pid}")        // delete http://localhost:9080/api/products/123
  public ApiResponse<String> delete( @PathVariable Long pid){
    ApiResponse<String> res = null;

    int row = productSVC.deleteById(pid);
    if(row == 1){
      res = ApiResponse.createApiResponse("00","success",null);
    }else{
      res = ApiResponse.createApiResponse("99","fail",null);
    }
    return res;
  }

  //목록
  @ResponseBody
  @GetMapping("/all")                // get http://localhost:9080/api/products/all
  public ApiResponse<List<Product>> all(){
    ApiResponse<List<Product>> res = null;
    List<Product> products = productSVC.findAll();
    if(products.size() == 0){
      res = ApiResponse.createApiResponse("01", "not found", null);
    }else{
      res = ApiResponse.createApiResponse("00", "success", products);
    }
    return res;
  }


}
