package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  //가입
  Member insertMember(Member member);

  //회원존재유무
  boolean isExist (String email);

  //회원조회
  Optional<Member> findByEmail(String email);

  //회원수정
  int updateMember(String email, Member member);
}
