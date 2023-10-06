package com.kh.demo1.web.form.member;

import lombok.Data;

//@Getter@Setter@ToString
@Data
public class JoinForm {
  private String email;
  private String passwd;
  private String tel;
  private String nickname;
  private String gender;
  private String hobby;
  private String region;
  private byte[] pic;
}
