package com.kh.demo1.domain.product.svc;

import com.kh.demo1.domain.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  //등록
  Long save(Product product);
  Long save(Product product, List<MultipartFile> attachFiles, List<MultipartFile> imageFiles);

  //조회
  Optional<Product> findById(Long productId);

  //목록
  List<Product> findAll();

  //단건삭제
  int deleteById(Long productId);

  //수정
  int updateById(Long productId, Product product);


}
