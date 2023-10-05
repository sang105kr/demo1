package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@Slf4j
public class LocalTest {
  @Test
  void local(){
    Locale[] locales = Locale.getAvailableLocales();
    for (Locale locale : locales) {
      log.info("locale={}",locale);
    }
  }
}
