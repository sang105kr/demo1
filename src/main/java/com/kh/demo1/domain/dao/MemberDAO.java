package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Member;

public interface MemberDAO {
  //가입
  Member insertMember(Member member);

  //회원존재유무
  boolean isExist (String email);
}
