package com.kh.demo1.web.req.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqChangePwd {
  @NotBlank
  private String email;
  @NotBlank
  private String beforePasswd;    //현재비밀번호
  @NotBlank
  private String afterPasswd;     //변경할 비밀번호
}
