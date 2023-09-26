package com.kh.demo1.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Slf4j    // log사용
@Repository // dao역할을 하는 클래스
@RequiredArgsConstructor  // final 멤버필드를 매개값으로 갖는 생성자를 자동 생성해줌
public class ProductDAOImpl implements ProductDAO {

  private final NamedParameterJdbcTemplate template;

//  @Autowired
//  public ProductDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  @Override
  public Long save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,pname,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, :pname , :quantity, :price) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(product); //
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"product_id"});

    long productId = keyHolder.getKey().longValue();    //상품아이디
    return productId;
  }

  @Override
  public Optional<Product> findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id,pname,quantity,price ");
    sql.append("  from product ");
    sql.append(" where product_id = :id ");

    //조회 : (단일행,단일열),(단일행,다중열),(다중행,단일열),(다중행,다중열)
    Map<String,Long> param = Map.of("id",productId);
    template.queryForObject(sql.toString(),param);


    return null;
  }
}
