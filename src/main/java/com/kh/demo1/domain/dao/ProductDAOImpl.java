package com.kh.demo1.domain.dao;

import com.kh.demo1.domain.dao.entity.Product;
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

import java.util.List;
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

    // SQL 파라미터 자동매핑 
    SqlParameterSource param = new BeanPropertySqlParameterSource(product); //
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"product_id"});

    long productId = keyHolder.getKey().longValue();    //상품아이디
    return productId;
  }

//  private RowMapper<Product> productRowMapper(){
//    return (rs,rowNum)->{
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setQuantity(rs.getLong("quantity"));
//      product.setPrice(rs.getLong("price"));
//
//      return product;
//    };
//  }

  private RowMapper<Product> productRowMapper(){
    return (rs,rowNum)->{
      Product product = new Product();
      product.setProductId(rs.getLong("product_id"));
      product.setPname(rs.getString("pname"));
      product.setQuantity(rs.getLong("quantity"));
      product.setPrice(rs.getLong("price"));

      return product;
    };
  }

  @Override
  public Optional<Product> findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id,pname,quantity,price ");
    sql.append("  from product ");
    sql.append(" where product_id = :id ");

    MyRowMapper myRowMapper = new MyRowMapper();
    try {
      //조회 : (단일행,단일열),(단일행,다중열),(다중행,단일열),(다중행,다중열)
      // SQL 파라미터 수동매핑 
      Map<String, Long> param = Map.of("id", productId);
      
      //RowMapper 수동 매핑
      Product product = template.queryForObject(sql.toString(), param, myRowMapper);
      return Optional.of(product);
    }catch(EmptyResultDataAccessException e){
      //조회결과가 없는경우
      return Optional.empty();
    }
  }

  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  select product_id, pname, quantity, price ");
    sql.append("    from product ");
    sql.append("order by product_id desc");

    //결과셋 자동 매핑 : BeanPropertyRowMapper
    List<Product> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Product.class));
    return list;
  }

  @Override
  public int deleteById(Long productId) {
    String sql = "delete from product where product_id = :productId";

    //SQL 파라미터 수동 매핑
    int deletedRowCnt = template.update(sql, Map.of("productId", productId));

    return deletedRowCnt;
  }

  @Override
  public int updateById(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("   set pname = :pname, quantity = :quantity, price = :price ");
    sql.append(" where product_id = :product_id ");

    // sql 파라미터 수동 매핑
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("pname", product.getPname())
        .addValue("quantity", product.getQuantity())
        .addValue("price", product.getPrice())
        .addValue("product_id",productId);
    
    int updatedRows = template.update(sql.toString(), param);
    return updatedRows;
  }
}
