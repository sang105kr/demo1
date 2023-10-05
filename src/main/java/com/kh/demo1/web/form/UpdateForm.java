package com.kh.demo1.web.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateForm {
  private Long productId;
  @NotBlank
  private String pname;
  private Long quantity;
  private Long price;
}
