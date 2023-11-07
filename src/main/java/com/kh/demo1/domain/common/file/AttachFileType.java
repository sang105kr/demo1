package com.kh.demo1.domain.common.file;

public enum AttachFileType {

  F010101("회원프로필사진"),
  
  F010201("공지사항"),
  F010202("자유게시판"),
  
  F010301("상품설명파일"),
  F010302("상품이미지파일");

  private final String description;

  AttachFileType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
