package com.kh.demo1.web.req.pubdata;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AedData {
  @NotBlank
  private String lat;
  @NotBlank
  private String lng;
  private String pageNo;
  private String numOfRows;
}
