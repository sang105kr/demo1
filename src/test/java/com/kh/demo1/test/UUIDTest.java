package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
public class UUIDTest {
  @Test
  void uuidTest(){
    String number = UUID.randomUUID().toString().substring(0,5);
    log.info("number={}", number);
  }
}
