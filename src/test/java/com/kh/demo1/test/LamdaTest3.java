package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class LamdaTest3 {

  Integer[] scores = null;
  @BeforeEach
  void beforeEach() {
    scores = new Integer[] {1,2,3,4,5};
  }

  @Test
  void test1(){
    Arrays.stream(scores).forEach(System.out::println);
    log.info("종료");
  }
}
