package com.kh.demo1.web.form.product;

import com.kh.demo1.domain.entity.UploadFile;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateForm {
  private Long productId;
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

  private List<MultipartFile> attachFiles;    //설명 파일
  private List<MultipartFile> imageFiles;    //이미지 첨부

  private List<UploadFile> attachedFiles;    //설명 파일
  private List<UploadFile> imagedFiles;    //이미지 첨부
}
