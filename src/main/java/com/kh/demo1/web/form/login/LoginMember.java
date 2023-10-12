package com.kh.demo1.web.form.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // 모든 멤버필드를 매개변수로 갖는 생성자를 만들어줌.
public class LoginMember {
  private String email;
  private String nickname;
  private String gubun;  //일반, vip, 관리자1, 관리자2

}
