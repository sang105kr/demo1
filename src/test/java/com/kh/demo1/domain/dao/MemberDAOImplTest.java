package com.kh.demo1.domain.dao;


import com.kh.demo1.domain.dao.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j            // console로그 출력
@SpringBootTest   // springboot 환경에서 테스트 진행
public class MemberDAOImplTest {

  @Autowired    // sc(스프링컨테이너)에서 동일타입의 객체를 찾아 주입받음.
  private MemberDAO memberDAO;

  @Test                   // 테스트 대상
  @DisplayName("가입")     // 테스트케이스 이름
  void insertMember(){
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

    Member insertedMember = memberDAO.insertMember(member);

    //output 데이터 검증
    log.info("insertedMember={}", insertedMember);
    
  }

  @Test
  @DisplayName("회원존재유무")
  void isExist(){
    String email = "test1@kh.com";
    boolean isExist = memberDAO.isExist(email);

    log.info("isExist={}",isExist);
    Assertions.assertThat(isExist).isTrue();
  }
}
