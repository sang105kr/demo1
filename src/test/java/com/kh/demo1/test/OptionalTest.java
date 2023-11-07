package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@Slf4j
public class OptionalTest {
  @Test
  void test1(){
    List<String> res = null;

    Optional<List<String>> optional = Optional.ofNullable(res);
    if(optional.isPresent()){
      log.info("있다");
    }else{
      log.info("없다");
    }
    if(optional.isEmpty()){
      log.info("없다");
    }else{
      log.info("있다.");
    }
  }
}
