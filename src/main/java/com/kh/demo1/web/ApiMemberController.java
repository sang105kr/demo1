package com.kh.demo1.web;

import com.kh.demo1.common.MailService;
import com.kh.demo1.common.MyUtil;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.api.ApiResponse;
import com.kh.demo1.web.req.member.ReqPwd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class ApiMemberController {

  private final MemberSVC memberSVC;
  private final MailService mailService;

  //비밀번호찾기
  //@ResponseBody
  @PostMapping("/findPwd")
  public ApiResponse<Object> findPwd(
      @RequestBody @Valid ReqPwd reqPwd,
      BindingResult bindingResult
  ) {
    ApiResponse<Object> res = null;

    //1) 유효성 체크
    if (bindingResult.hasErrors()) {
      return MyUtil.validChkApiReq(bindingResult);
    }

    //2) 비밀번호 찾기
    boolean hasPasswd = memberSVC.hasPasswd(reqPwd.getEmail(), reqPwd.getTel());
    //2-1) 존재
    if(hasPasswd) {
      //2-1-1) 임시비밀번호 생성
      String tmpPasswd = UUID.randomUUID().toString().substring(0, 5); //xxdy7

      //2-1-2) 회원 비밀번호를 임시비밀번호로 변경
      memberSVC.changePasswd(reqPwd.getEmail(),tmpPasswd);

      //2-1-3) 임시비밀번호 메일 전송
      StringBuffer str = new StringBuffer();
      str.append("<html>");
      str.append("<p><b>");
      str.append(tmpPasswd);
      str.append("</b></p>");
      str.append("<p>위 임시번호로 로그인후 비밀번호 변경 바랍니다.</p>");
      str.append("<a href='http://localhost:9080/login'>로그인</a>");
      str.append("</html>");
      mailService.sendMail("sang105kr@gmail.com","임시비밀번호",str.toString());

      res = ApiResponse.createApiResponse("00", "success, 임시비밀번호가 이메일로 발송됨!", null);
    }else {
      //2-2) 미존재
      res = ApiResponse.createApiResponse("01", "not found", null);
    }
    return  res;
  }
}
