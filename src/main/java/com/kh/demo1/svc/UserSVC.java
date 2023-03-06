package com.kh.demo1.svc;

import com.kh.demo1.web.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserSVC {
  private Map<Long,UserDTO> users = new LinkedHashMap<>();
  private static long id = 0;

  private long incressId() {
    return ++UserSVC.id;
  }
  //등록
  public long add(UserDTO userDTO) {
    long id = incressId();
    userDTO.setId(id);
    users.put(id,userDTO);
    return id;
  }
  //조회
  public UserDTO findById(long id){
    return users.get(id);
  }
  //수정
  public UserDTO update(long id, UserDTO userDTO) {
    users.put(id,userDTO);
    return users.get(id);
  }

  //삭제
  public void delete(long id) {
    users.remove(id);
  }

  //목록
  public ArrayList<UserDTO> list() {
    Collection<UserDTO> values = users.values();
    ArrayList<UserDTO> arr = new ArrayList<>();
    arr.addAll(values);
    return arr;
  }
}
