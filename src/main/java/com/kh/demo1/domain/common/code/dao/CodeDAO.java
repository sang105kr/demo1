package com.kh.demo1.domain.common.code.dao;

import com.kh.demo1.domain.entity.Code;

import java.util.List;

public interface CodeDAO {

  /**
   * 하위코드 반환 by 부모코드
   * @param pcodeId 부모코드
   * @return 하위코드
   */
  List<Code> findCodesByPcodeId(String pcodeId);

  /**
   * 전체코드
   * @return
   */
  List<Code> find();
}
