package com.kh.demo1.svc;

import com.kh.demo1.dao.Product;

public interface ProductSVC {
  //등록
  Long save(Product product);
}
