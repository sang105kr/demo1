package com.kh.demo1.web.form;

import lombok.Data;

@Data
public class UpdateForm {
  private Long productId;
  private String panme;
  private Long quantity;
  private Long price;
}
