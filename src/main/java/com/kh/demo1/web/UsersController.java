package com.kh.demo1.web;

import com.kh.demo1.svc.UserSVC;
import com.kh.demo1.web.api.ApiResponse;
import com.kh.demo1.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
//@Controller // view + data
@RestController // data
@RequiredArgsConstructor
public class UsersController {

  private final UserSVC userSVC;

  //등록
  @PostMapping("/users")
  public ApiResponse<Long> add(
      @RequestBody UserDTO userDTO
  ){
    log.info("user={}", userDTO);
    //1) 요청데이터 검증
    //2) svc 데이터 이관
    long id = userSVC.add(userDTO);

    ApiResponse<Long> res = ApiResponse.createApiResMsg("00", "성공",  Long.valueOf(id));
    return res;
  }

  //조회
  @GetMapping("/users/{id}")
  public ApiResponse<UserDTO> findById(
      @PathVariable("id") long id
  ){

    UserDTO user = userSVC.findById(id);
    ApiResponse<UserDTO> res = ApiResponse.createApiResMsg("00", "성공", user );
    return res;
  }

  //수정
  @PatchMapping("/users/{id}")
  public ApiResponse<UserDTO> update( @PathVariable long id, @RequestBody UserDTO userDTO) {
    UserDTO updatedUser = userSVC.update(id, userDTO);
    ApiResponse<UserDTO> res = ApiResponse.createApiResMsg("00", "성공", updatedUser );
    return res;
  }

  //삭제
  @DeleteMapping("/users/{id}")
  public ApiResponse<String> delete(@PathVariable long id) {
    userSVC.delete(id);
    ApiResponse<String> res = ApiResponse.createApiResMsg("00", "성공", "회원삭제" );
    return res;
  }

  //목록
  @GetMapping("/users")
  public ApiResponse<ArrayList<UserDTO>> list(){
    ArrayList<UserDTO> users = userSVC.list();
    ApiResponse<ArrayList<UserDTO>> res = ApiResponse.createApiResMsg("00", "성공", users );
    return res;
  }
}
