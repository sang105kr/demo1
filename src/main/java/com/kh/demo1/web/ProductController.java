package com.kh.demo1.web;

import com.kh.demo1.domain.dao.Product;
import com.kh.demo1.domain.svc.ProductSVC;
import com.kh.demo1.web.form.AllForm;
import com.kh.demo1.web.form.DetailForm;
import com.kh.demo1.web.form.SaveForm;
import com.kh.demo1.web.form.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")   // http://localhost:9080/products
@RequiredArgsConstructor       // 멤버필드중 final만 생성자 매개변수로하여 생성자를 자동 만들어준다.
public class ProductController {

  private final ProductSVC productSVC;

//  @Autowired
//  public ProductController(ProductSVC productSVC) {
//    this.productSVC = productSVC;
//  }

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
//      @RequestParam("quantity") Long quantity,
//      @RequestParam("price") Long price
      SaveForm saveForm, RedirectAttributes redirectAttributes
  ){
    log.info("add호출됨!={}",saveForm);
    // 요청데이터 유효성 체크
    // 상품등록
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());
    Long productId = productSVC.save(product);

    log.info("상품아이디={}",productId);
    redirectAttributes.addAttribute("id", productId);

    return "redirect:/products/{id}/detail";   // 302 GET http://localhost:9080/products/1/detail
  }

  //조회
  @GetMapping("/{id}/detail")  //GET http://localhost:9080/products/1/detail
  public String findById(
      @PathVariable("id") Long id,
      Model model){
    //상품조회
    Optional<Product> findedProduct = productSVC.findById(id);
    Product product = findedProduct.orElseThrow(); // optional에 product가 있으면 값을 가져오고 product없으면 예외발생

    DetailForm detailForm = new DetailForm();
    detailForm.setProductId(product.getProductId());
    detailForm.setPname(product.getPname());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("detailForm",detailForm);
    return "product/detailForm";
  }

  //목록
  @GetMapping         //GET http://localhost:9080/products
  public String findAll(Model model){
    log.info("findAll()호출됨!");
    //상품목록조회

    List<Product> list = productSVC.findAll();
    List<AllForm> all = new ArrayList<>();
    for(Product product :list){
      AllForm allForm = new AllForm();
      allForm.setProductId(product.getProductId());
      allForm.setPname(product.getPname());
      all.add(allForm);
    }
    model.addAttribute("all",all);

    return "product/all";
  }

  //삭제
//  @GetMapping("/{id}/del")      // GET http://localhost:9080/products/1
  @DeleteMapping("/{id}")      // Delete http://localhost:9080/products/1
  public String deleteById(@PathVariable("id") Long id){

    int deletedRowCnt = productSVC.deleteById(id);

    return "redirect:/products";        // 302 get  redirectUrl : http://localhost:9080/products
  }

  //수정양식
  @GetMapping("/{id}")        // GET http://localhost:9080/products/1
  public String updateForm(
      @PathVariable("id") Long id,
      Model model){
    log.info("updateForm()호출됨!");
    Optional<Product> findedProduct = productSVC.findById(id);

    Product product = findedProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setProductId(product.getProductId());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("updateForm", updateForm);
    return "product/updateForm";
  }

  //수정처리
  @PatchMapping("/{id}")    //Patch http://localhost:9080/products/1
  public String update(
      @PathVariable("id") Long productId,
      UpdateForm updateForm, RedirectAttributes redirectAttributes){
    log.info("update()호출됨!");
    log.info("updateForm={}",updateForm);
    
    //상품수정
    Product product = new Product();
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());
    int updatedRow = productSVC.updateById(productId, product);

    //상품조회 리다이렉트
    redirectAttributes.addAttribute("id",productId);
    return "redirect:/products/{id}/detail";   // 302 get http://localhost:9080/products/1/detail
  }
}
