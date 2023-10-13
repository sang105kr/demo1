package com.kh.demo1.web.form.mypage.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ModifyForm {
  private Long memberId;
  private String email;
  @NotBlank
  private String passwd;
  private String tel;
  private String nickname;
  private String gender;
  private List<String> hobby;
  private String region;
  private byte[] pic;
}
