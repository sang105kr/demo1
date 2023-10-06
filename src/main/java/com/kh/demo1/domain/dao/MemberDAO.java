package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Member;

public interface MemberDAO {
  //가입
  Member insertMember(Member member);
}
