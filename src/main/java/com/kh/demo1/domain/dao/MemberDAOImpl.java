package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor  // final멤버필드를 매개변수로 갖는 생성자 자동 생성
public class MemberDAOImpl implements MemberDAO {

//  @Autowired  //필드주입
  private final NamedParameterJdbcTemplate template;

//  @Autowired  //생성자주입
//  public MemberDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  //가입
  @Override
  public Member insertMember(Member member) {
    //1) sql
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun) ");
    sql.append("values(member_member_id_seq.nextval, :email, :passwd, :tel, :nickname, :gender, :hobby, :region, :gubun) ");

    //2) sql실행
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int instertedRows = template.update(sql.toString(),param,keyHolder,new String[]{"member_id"});

    Long memberId = keyHolder.getKey().longValue();
    member.setMemberId(memberId);

    return member;
  }

  //회원 존재 유무
  @Override
  public boolean isExist(String email) {
    String sql = "select count(*) from member where email = :email ";
    Map<String,String> param = Map.of("email",email);
    Integer cnt = template.queryForObject(sql, param, Integer.class); // 1,0

    return cnt == 1 ? true: false;
  }
}
