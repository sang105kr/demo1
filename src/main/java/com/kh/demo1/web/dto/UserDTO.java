package com.kh.demo1.web.dto;

import lombok.Data;

@Data
public class UserDTO {
  private long id;
  private String email;
  private String name;
  private int age;
}
