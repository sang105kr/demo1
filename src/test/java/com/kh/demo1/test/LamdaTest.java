package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

interface Soundable {
  String sound(String animal);
}

class SoundableImpl implements Soundable{
  @Override
  public String sound(String animal) {
    String soundOfAnimal = "";
    switch (animal){
      case "고양이":
        soundOfAnimal = "야옹";
      case "호랑이":
        soundOfAnimal = "어흥";
      case "오리":
        soundOfAnimal = "꽥꽥";
    }
    return soundOfAnimal;
  }
}

@Slf4j
public class LamdaTest {
  @Test
  @DisplayName("이름있는 구현클래스")
  void test(){
    Soundable soundable = new SoundableImpl();
    String sound = soundable.sound("고양이");
    log.info("동물울음소리={}",sound);
  }
  
  @Test
  @DisplayName("익명 구현클래스")
  void test2(){
    Soundable soundable = new Soundable() {
      @Override
      public String sound(String animal) {
        String soundOfAnimal = "";
        switch (animal){
          case "고양이":
            soundOfAnimal = "야옹";
          case "호랑이":
            soundOfAnimal = "어흥";
          case "오리":
            soundOfAnimal = "꽥꽥";
        }
        return soundOfAnimal;
      }
    };
    String sound = soundable.sound("고양이");
    log.info("동물울음소리={}",sound);
  }
  @Test
  @DisplayName("람다식")
  void test3(){
    Soundable soundable =
      (String animal)->{
        String soundOfAnimal = "";
        switch (animal){
          case "고양이":
            soundOfAnimal = "야옹";
          case "호랑이":
            soundOfAnimal = "어흥";
          case "오리":
            soundOfAnimal = "꽥꽥";
        }
        return soundOfAnimal;
      };
    String sound = soundable.sound("고양이");
    log.info("동물울음소리={}",sound);
  }
}
