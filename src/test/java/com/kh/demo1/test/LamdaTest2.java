package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LamdaTest2 {
  @Test
  void test(){

    interface SumIf { int sum(int x, int y); }

//    SumIf sum1 = new SumIf() {
//      @Override
//      public int sum(int x, int y) {
//        return x+y;
//      }
//    };

    SumIf sum2 = (int x, int y)-> x + y;
    log.info("합계={}",sum2.sum(10,20));

  }
}
