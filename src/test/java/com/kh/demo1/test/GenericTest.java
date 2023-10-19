package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

class Apple {
  String type;

  Apple(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
class Banana {}

//제너릭 타입 : 타입을 일반화해서 정의하는 문법
//            구체적인 타입은 사용시점에 결정되고 컴파일 시점에 반영됨.
//            장점 : 타입을 일반화해서 정의, 컴파일시 타입체크, 타입변환 비용 제거
class Box<A> {
  A field;

  public A getField() {
    return field;
  }

  public void setField(A field) {
    this.field = field;
  }
}

class Box2 {
  Object field;
}
@Slf4j
public class GenericTest {
  @Test
  void test1() {
    Box<Apple> box = new Box<>();
    box.field = new Apple("부사");
//    box.field = new Banana();    // 컴파일시 타입체크
    log.info("box={}", box.field.getClass());  //Apple
    log.info("typeOfApple={}", box.field.getType());
  }
  @Test
  void test2() {
    Box2 box = new Box2();
    box.field = new Apple("부사");
//    box.field = new Banana();       //컴파일시 타입체크 불가
    log.info("box={}", box.field.getClass());  //Object
    log.info("typeOfApple={}", ((Apple)(box.field)).getType());
  }
}
