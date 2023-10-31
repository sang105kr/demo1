package com.kh.demo1.web.req.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReqEmail {
  @NotBlank
  @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
  private String tel;
}
