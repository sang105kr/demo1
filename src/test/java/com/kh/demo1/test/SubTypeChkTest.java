package com.kh.demo1.test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class SubTypeChkTest {
  @Test
  void test1(){
    Class<?> expectedType = Collection.class;

    Assertions.assertThat(ClassUtils.isAssignable(expectedType, ArrayList.class)).isTrue();
  }
}
