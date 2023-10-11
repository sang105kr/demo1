package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.entity.Member;

public interface MemberSVC {
  //가입
  Member join(Member member);

  //회원존재유무
  boolean isMember(String email);
}
