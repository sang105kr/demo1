package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.form.member.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/members")       //  http://localhost:9080/members
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  //가입처리
  @PostMapping("/add")
  public String join(JoinForm joinForm){

    log.info("join()호출");
    log.info("joinForm={}",joinForm);

    Member member = new Member();
    member.setEmail(joinForm.getEmail());
    member.setPasswd(joinForm.getPasswd());
    member.setTel(joinForm.getTel());
    member.setNickname(joinForm.getNickname());
    member.setGender(joinForm.getGender());
    member.setHobby(joinForm.getHobby());
    member.setRegion(joinForm.getRegion());
    member.setGubun("M0101"); //일반회원
    Member joinedMember = memberSVC.join(member);

    return "index"; // view 이름
  }

}
