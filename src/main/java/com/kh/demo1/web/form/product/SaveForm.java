package com.kh.demo1.web.form.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SaveForm {
  @NotBlank // null, 빈문자열("")를 허용 안함, 문자열 타입만 사용
  @Size(min=1,max=10)
  private String pname;
  @NotNull
  @Positive   //양수
//  @Max(value=1000,message="최대값은 1000초과 불과")  //최대 1000초과 불과!
  private Long quantity;

  @NotNull
  @Positive
  @Min(100)
  @Max(1000000)
  private Long price;

  private MultipartFile file;           //단일 파일첨부
  private List<MultipartFile> files;    //멀티파일 첨부

}
