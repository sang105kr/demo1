package com.kh.demo1.web.form.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

//@Getter@Setter@ToString
@Data
public class JoinForm {
  @Email
  @Max(50)
  private String email;

  @Size(min=4,max=12)
  @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")
  private String passwd;
  private String tel;
  private String nickname;
  private String gender;
  private List<String> hobby;
  private String region;
  private byte[] pic;
}
