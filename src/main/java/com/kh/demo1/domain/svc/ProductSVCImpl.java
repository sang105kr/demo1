package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.Product;
import com.kh.demo1.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
    return productDAO.save(product);
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
}
