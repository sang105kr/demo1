package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.MemberDAO;
import com.kh.demo1.domain.dao.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  //가입
  @Override
  public Member join(Member member) {
    return memberDAO.insertMember(member);
  }

}
