package com.kh.demo1.domain.dao;


import com.kh.demo1.domain.dao.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyRowMapper implements RowMapper<Product> {
  @Override
  public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
    Product product = new Product();
    product.setProductId(rs.getLong("product_id"));
    product.setPname(rs.getString("pname"));
    product.setQuantity(rs.getLong("quantity"));
    product.setPrice(rs.getLong("price"));

    return product;
  }
}
