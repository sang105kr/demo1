package com.kh.demo1.web.form.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SaveFormOld {
  @NotBlank(message="필수입력 필드입니다.")  // null, 빈문자열("")를 허용 안함, 문자열 타입만 사용
  @Size(min=1,max=10,message="1~10자까지 가능합니다")
  private String pname;
  @NotNull(message="필수입력 필드입니다.")
  @Positive(message="양수 여야합니다.")   //양수
//  @Max(value=1000,message="최대값은 1000초과 불과")  //최대 1000초과 불과!
  private Long quantity;

  @NotNull(message="필수입력 필드입니다.")
  @Positive(message="양수 여야합니다.")
  @Min(value=100,message = "최소 100이상 여야합니다.")
  @Max(value=1000000,message = "최대 100만원을 초과할수 없습니다.")
  private Long price;
}
