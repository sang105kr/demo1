package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.MemberDAO;
import com.kh.demo1.domain.dao.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

  //회원존재유무
  @Override
  public boolean isMember(String email) {
    return memberDAO.isExist(email);
  }

  //회원조회
  @Override
  public Optional<Member> findByEmail(String email) {
    return memberDAO.findByEmail(email);
  }

  //회원수정
  @Override
  public int modify(String email, Member member) {
    return memberDAO.updateMember(email,member);
  }

  //아이디찾기
  @Override
  public Optional<String> findEmailByTel(String tel) {
    return memberDAO.findEmailByTel(tel);
  }

  //비밀번호 존재유무
  @Override
  public boolean hasPasswd(String email, String tel) {
    return memberDAO.hasPasswd(email, tel);
  }

  //비밀번호 변경
  @Override
  public int changePasswd(String email, String passwd) {
    return memberDAO.changePasswd(email,passwd);
  }
}
