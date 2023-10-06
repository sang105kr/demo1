package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Product;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Data
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

  @Test
  @DisplayName("목록")
  void findAll(){
    List<Product> list = productDAO.findAll();
    log.info("목록={}",list);
    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("단건삭제")
  void deleteById(){
    Long productId = 100L;
    int deletedRowCnt = productDAO.deleteById(productId);

    Assertions.assertThat(deletedRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("상품수정")
  void updateById(){
    Long productId = 121L;
    Product product = new Product();
    product.setPname("상품명수정");
    product.setQuantity(10L);
    product.setPrice(100L);
    int updatedRows = productDAO.updateById(productId, product);

    Optional<Product> findedProduct = productDAO.findById(productId);
    Product updatedProduct = findedProduct.orElseThrow();

    Assertions.assertThat(updatedProduct.getPname()).isEqualTo("상품명수정");
    Assertions.assertThat(updatedProduct.getQuantity()).isEqualTo(10L);
    Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(100L);
  }
}

