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

  //아이디찾기
  Optional<String> findEmailByTel(String tel);

  //비밀번호 유무확인
  boolean hasPasswd(String email, String tel);

  //비밀번호 변경
  int changePasswd(String email, String tmpPasswd);
}
