package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SubstringTest {
  @Test
  void test(){
    log.info("value={}","M01A1".substring(3,4));
  }
}
