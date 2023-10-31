package com.kh.demo1.domain.dao;


import com.kh.demo1.domain.dao.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

  @Test
  @DisplayName("이메일로 회원조회 O")
  void findByEmail(){
    String email = "test1@kh.com";
    Optional<Member> optionalMember = memberDAO.findByEmail(email);
    boolean isEmpty = optionalMember.isEmpty();
    Assertions.assertThat(isEmpty).isFalse();

    log.info("member={}", optionalMember.get());
  }

  @Test
  @DisplayName("이메일로 회원조회 X")
  void findByEmail2(){
    String email = "xxx@xxx.xxx";
    Optional<Member> optionalMember = memberDAO.findByEmail(email);
    boolean isEmpty = optionalMember.isEmpty();
    Assertions.assertThat(isEmpty).isTrue();
  }

  @Test
  @DisplayName("회원수정")
  void modifyMember(){
    String email = "test1@kh.com";
    Member member = new Member();
    member.setTel("999-9999-9999");
    member.setNickname("홍길동2");
    member.setGender("남자");
    member.setHobby("등산");
    member.setRegion("A0201");

    int rows = memberDAO.updateMember(email, member);

    Optional<Member> optionalMember = memberDAO.findByEmail(email);
    Member updatedMember = optionalMember.get();

    Assertions.assertThat(updatedMember.getTel()).isEqualTo("999-9999-9999");
    Assertions.assertThat(updatedMember.getNickname()).isEqualTo("홍길동2");
    Assertions.assertThat(updatedMember.getGender()).isEqualTo("남자");
    Assertions.assertThat(updatedMember.getHobby()).isEqualTo("등산");
    Assertions.assertThat(updatedMember.getRegion()).isEqualTo("A0201");
  }

  @Test
  @DisplayName("아이디찾기 success")
  void findEmailByTel(){
    String tel = "010-1234-5678";
    Optional<String> findedEmail = memberDAO.findEmailByTel(tel);
    if(findedEmail.isPresent()){
      String email = findedEmail.get();
      log.info("email={}", email);
      Assertions.assertThat(email).isNotBlank();
    }
  }

  @Test
  @DisplayName("아이디찾기 fail")
  void findEmailByTel2(){
    String tel = "010-0000-0000";
    Optional<String> findedEmail = memberDAO.findEmailByTel(tel);
    Assertions.assertThat(findedEmail.isEmpty()).isTrue();

  }

  @Test
  @DisplayName("비밀번호존재유뮤 has")
  void hasPasswd() {
    String email = "test3@kh.com";
    String tel = "010-1234-5678";
    boolean hasPasswd = memberDAO.hasPasswd(email, tel);
    Assertions.assertThat(hasPasswd).isTrue();
  }

  @Test
  @DisplayName("비밀번호존재유뮤 not has")
  void hasPasswd2() {
    String email = "xxx@xx.xxx";
    String tel = "xxx-xxxx-xxxx";
    boolean hasPasswd = memberDAO.hasPasswd(email, tel);
    Assertions.assertThat(hasPasswd).isFalse();
  }

  @Test
  @DisplayName("비밀번호변경")
  @Transactional  // sql실행후 rollbak함. (따라서 실제 테이블의 데이터 변경은 없음)
  void changePasswd() {
    String email = "test3@kh.com";
    String tmpPasswd = "xxx";
    memberDAO.changePasswd(email, tmpPasswd);
    Optional<Member> optional = memberDAO.findByEmail(email);
    if (optional.isPresent()) {
      Member member = optional.get();
      Assertions.assertThat(member.getPasswd()).isEqualTo(tmpPasswd);
    }else{
      Assertions.fail("회원정보 없음");
    }
  }
}
