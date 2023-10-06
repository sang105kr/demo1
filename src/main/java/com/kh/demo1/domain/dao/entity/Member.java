package com.kh.demo1.domain.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
  private Long memberId;    //  MEMBER_ID	NUMBER
  private String email;     //  EMAIL	VARCHAR2(50 BYTE)
  private String passwd;    //  PASSWD	VARCHAR2(12 BYTE)
  private String tel;       //  TEL VARCHAR2(14 BYTE)
  private String nickname;  //  NICKNAME	VARCHAR2(30 BYTE)
  private String gender;    //  GENDER	VARCHAR2(6 BYTE)
  private String hobby;     //  HOBBY	VARCHAR2(300 BYTE)
  private String region;    //  REGION	VARCHAR2(11 BYTE)
  private String gubun;     //  GUBUN	VARCHAR2(11 BYTE)
  private byte[] pic;       //  PIC	BLOB
  private LocalDateTime cdate;  //  CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  //  UDATE	TIMESTAMP(6)
}
