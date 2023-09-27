package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.Product;
import com.kh.demo1.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

  @Override
  public Optional<Product> findById(Long productId) {
    Optional<Product> product = productDAO.findById(productId);
    return product;
  }
}
