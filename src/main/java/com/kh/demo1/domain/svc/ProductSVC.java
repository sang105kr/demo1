package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.Product;

import java.util.Optional;

public interface ProductSVC {
  //등록
  Long save(Product product);

  //조회
  Optional<Product> findById(Long productId);

}
