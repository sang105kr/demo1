package com.kh.demo1.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired  // 스프링 컨테이너에서 해당 타입의 객체를 주입받음.
  ProductDAO productDAO;

  //등록
  @Test
  @DisplayName("상품등록")
  void save(){
    Product product = new Product();
    product.setPname("상품1");
    product.setQuantity(10L);
    product.setPrice(10000L);
    Long productId = productDAO.save(product);
    log.info("상품아이디={}", productId);

    Assertions.assertThat(productId).isGreaterThan(0L);
  }

  //조회
  @Test
  @DisplayName("조회")
  void findById(){
    Optional<Product> product = productDAO.findById(73L);
//    if(product.isPresent()){
//      log.info("product={}",product.get());
//    }else{
//      log.info("조회한결과 없음");
//    }
    Product findedProduct = product.orElseThrow();
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("자동차2");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(1L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(100000000L);
  }
}

