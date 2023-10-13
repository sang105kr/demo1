package com.kh.demo1.web.form.mypage.member;

import lombok.Data;

import java.util.List;

@Data
public class DetailForm {
  private Long memberId;
  private String email;
  private String tel;
  private String nickname;
  private String gender;
  private List<String> hobby;
  private String region;
  private byte[] pic;
}
