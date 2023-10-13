package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  //가입
  Member join(Member member);

  //회원존재유무
  boolean isMember(String email);

  //회원조회
  Optional<Member> findByEmail(String email);

  //회원수정
  int modify(String email, Member member);
}
