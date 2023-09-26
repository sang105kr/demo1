package com.kh.demo1.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
