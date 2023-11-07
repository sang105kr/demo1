package com.kh.demo1.web.form.product;

import com.kh.demo1.domain.entity.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class DetailForm {
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;

  private List<UploadFile> attachFiles;    //설명 파일
  private List<UploadFile> imageFiles;    //이미지 첨부
}
