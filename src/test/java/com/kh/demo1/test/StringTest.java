package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringTest {
  @Test
  void test1(){
    log.info("M01A1".substring(0,4));
  }
}
