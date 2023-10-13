package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

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

  //회원 조회
  @Override
  public Optional<Member> findByEmail(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id,email,passwd, ");
    sql.append("       tel,nickname,gender,hobby,region,gubun, ");
    sql.append("       pic,cdate,udate ");
    sql.append("from member ");
    sql.append("where email = :email ");

    Map<String, String> param = Map.of("email", email);
    // Member.class 필드명과 Result 컬럼명과 동일한경우 자동 매핑
    RowMapper rowMapper = new BeanPropertyRowMapper(Member.class);
    try {
      // queryForObject() : 단일행 다중열 레코드의 결과 셋을 가져올때 사용하는 메소드
      //                     결과셋이 없으면 EmptyResultDataAccessException 예외를 발생
      Member findedMember = (Member) template.queryForObject(sql.toString(), param, rowMapper);
      return Optional.of(findedMember);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  //회원수정
  @Override
  public int updateMember(String email, Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("set tel = :tel, ");
    sql.append("    nickname = :nickname, ");
    sql.append("    gender = :gender, ");
    sql.append("    hobby = :hobby, ");
    sql.append("    region = :region ");
    sql.append("where email = :email ");

    SqlParameterSource parm = new MapSqlParameterSource()
        .addValue("tel",member.getTel())
        .addValue("nickname",member.getNickname())
        .addValue("gender",member.getGender())
        .addValue("hobby",member.getHobby())
        .addValue("region",member.getRegion())
        .addValue("email",email);

    //rows : 업데이트된 레코드 수
    int rows = template.update(sql.toString(), parm);

    return rows;
  }
}
