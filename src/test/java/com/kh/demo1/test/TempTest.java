package com.kh.demo1.test;

import com.kh.demo1.domain.common.file.AttachFileType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class TempTest {
  @Test
  void t1(){
    AttachFileType[] values = AttachFileType.values();
    Arrays.stream(values).forEach(ele->log.info(ele.name()));
    AttachFileType a = AttachFileType.valueOf("F010301");
    log.info(a.name());
  }
}
