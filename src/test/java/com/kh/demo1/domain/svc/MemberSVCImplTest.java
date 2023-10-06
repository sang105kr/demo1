package com.kh.demo1.domain.svc;

import com.kh.demo1.domain.dao.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MemberSVCImplTest {

  @Autowired
  private MemberSVC memberSVC;

  @Test
  @DisplayName("가입")
  void join(){
    //input 데이터
    Member member = new Member();
    member.setEmail("test10@kh.com");
    member.setPasswd("1234");
    member.setTel("010-2222-2222");
    member.setNickname("테스터10");
    member.setHobby("골프,등산");
    member.setGender("남자");
    member.setRegion("A0204");    //울산
    member.setGubun("M0101");     //일반고객

    Member joinedMember = memberSVC.join(member);

    log.info("joinedMember={}",joinedMember);
  }
}
