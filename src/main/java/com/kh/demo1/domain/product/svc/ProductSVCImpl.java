package com.kh.demo1.domain.product.svc;

import com.kh.demo1.domain.common.file.AttachFileType;
import com.kh.demo1.domain.common.file.svc.UploadFileSVC;
import com.kh.demo1.domain.entity.Product;
import com.kh.demo1.domain.entity.UploadFile;
import com.kh.demo1.domain.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;
  private final UploadFileSVC uploadFileSVC;

//  @Autowired
//  public ProductSVCImpl(ProductDAO productDAO) {
//    this.productDAO = productDAO;
//  }

  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  @Transactional
  @Override
  public Long save(Product product, List<MultipartFile> attachFiles, List<MultipartFile> imageFiles) {    //첨부파일

    //상품등록
    Long productId = save(product);
    
    //첨부등록
    if (attachFiles.size() > 0 || imageFiles.size() > 0) {

      ///메타정보 및 물리파일 저장
      List<UploadFile> convertedAattachFiles = uploadFileSVC.convert(attachFiles, AttachFileType.F010301);
      List<UploadFile> convertedImageFiles  = uploadFileSVC.convert(imageFiles, AttachFileType.F010302);
      convertedAattachFiles.addAll(convertedImageFiles);
      convertedAattachFiles.stream().forEach(file->file.setRid(productId));
      uploadFileSVC.addFiles(convertedAattachFiles);
    }

    return productId;
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productDAO.findById(productId);
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public int deleteById(Long productId) {
    return productDAO.deleteById(productId);
  }

  @Override
  public int updateById(Long productId, Product product) {
    return productDAO.updateById(productId,product);
  }
}
