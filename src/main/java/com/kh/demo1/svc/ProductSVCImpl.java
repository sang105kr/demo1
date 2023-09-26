package com.kh.demo1.svc;

import com.kh.demo1.dao.Product;
import com.kh.demo1.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

//  @Autowired
//  public ProductSVCImpl(ProductDAO productDAO) {
//    this.productDAO = productDAO;
//  }

  @Override
  public Long save(Product product) {
    Long productId = productDAO.save(product);
    return productId;
  }
}
