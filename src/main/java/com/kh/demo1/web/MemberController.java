package com.kh.demo1.web;

import com.kh.demo1.common.MailService;
import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.common.MyUtil;
import com.kh.demo1.web.api.ApiResponse;
import com.kh.demo1.web.form.member.JoinForm;
import com.kh.demo1.web.req.member.ReqEmail;
import com.kh.demo1.web.req.member.ReqPwd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/members")       //  http://localhost:9080/members
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;
  private final MailService mailService;

  //취미
  @ModelAttribute("hobbys")   // model.addAttributes("hobbys",hobbys);
  public Map<String,String> hobbys(){
    Map<String,String> hobbys = new LinkedHashMap<>();
    hobbys.put("swim","수영");
    hobbys.put("climing", "등산");
    hobbys.put("golf", "골프");
    return hobbys;
  }

  //지역
  @ModelAttribute("region")
  public Map<String,String> region(){
    Map<String, String> region = new LinkedHashMap<>();
    region.put("A0201","서울");
    region.put("A0202","부산");
    region.put("A0203","대구");
    region.put("A0204","울산");
    return region;
  }
  
  //가입화면
  @GetMapping("/add")      //  GET http://localhost:9080/members/add
  public String joinForm(Model model){

    model.addAttribute("joinForm",new JoinForm());
    return "member/joinForm";
  }


  //가입처리
  @PostMapping("/add")      //  POST http://localhost:9080/members/add
  public String join(
      @Valid                          //form객체의 필드 유효성 체크, 생략하면 유효성 체크 안함.
      @ModelAttribute                 //Model객체에 form객체타입을 키로 form객체 값을 벨류로 저장함.
      JoinForm joinForm,
      BindingResult bindingResult     // 오류필드와 오류메시지를 담고있는 객체로 form객체 바로다음에 와야함.
  ){

    log.info("join()호출");
    log.info("joinForm={}",joinForm);

    //어노테이션 기반의 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "member/joinForm";
    }

    //로직 검증
    //필드 검증
    //1) 비밀번호확인
    if(!joinForm.getPasswd().equals(joinForm.getPasswdChk())){
      bindingResult.rejectValue("passwdChk", "member");
    }
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "member/joinForm";
    }
    //2) 회원 중복체크
    boolean isMember = memberSVC.isMember(joinForm.getEmail());
    if(isMember) {
      bindingResult.rejectValue("email", "member",null);
    }

    //글로벌 검증
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "member/joinForm";
    }
    Member member = new Member();
    member.setEmail(joinForm.getEmail());
    member.setPasswd(joinForm.getPasswd());
    member.setTel(joinForm.getTel());
    member.setNickname(joinForm.getNickname());
    member.setGender(joinForm.getGender());
    List<String> hobbys = joinForm.getHobby();

    //["수영","등산"] => "수영,등산"
    member.setHobby(MyUtil.listToString(joinForm.getHobby()));
    member.setRegion(joinForm.getRegion());
    member.setGubun("M0101"); //일반회원
    Member joinedMember = memberSVC.join(member);

    return "index"; // view 이름
  }

  //이메일 인증
  @ResponseBody     //반환값 응답 메세지 바디에 직접 쓰기
  @GetMapping("/emailChk/{email}")
  public String emailChk(@PathVariable("email") String email){
    log.info("email={}",email);
    //1) 인증번호(랜덤코드 발생)
    String mailAuthNum = UUID.randomUUID().toString().substring(0,5); // 23dd3

    //2) 메일전송
    StringBuffer str = new StringBuffer();
    str.append("<html>");
    str.append("<p><b>");
    str.append(mailAuthNum);
    str.append("</b></p>");
    str.append("<p>위 인증번호를 회원가입시 입력바랍니다</p>");
    str.append("</html>");
    mailService.sendMail("sang105kr@gmail.com","이메일인증",str.toString());

    return mailAuthNum;
  }

  //아이디찾기 form
  @GetMapping("/findEmail")
  public String findEmailForm(){

    return "member/findEmail";
  }

  //아이디찾기
  @ResponseBody
  @PostMapping("/findEmail")
  public ApiResponse<Object> findEmail(
      @RequestBody @Valid ReqEmail reqEmail,
      BindingResult bindingResult){
    ApiResponse<Object> res = null;

    log.info("tel={}",reqEmail.getTel());
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return MyUtil.validChkApiReq(bindingResult); //rtcd : "02"
    }

    Optional<String> optional = memberSVC.findEmailByTel(reqEmail.getTel());
    if(optional.isPresent()){
      String email = optional.get();
      res = ApiResponse.createApiResponse("00","success",email);

    }else{
      res = ApiResponse.createApiResponse("01","not found",null);
    }

    return res;
  }
  
  //비밀번호찾기 form
  @GetMapping("/findPwd")
  public String findPwdForm(){
    return "member/findPwd";
  }

  //비밀번호찾기
  @ResponseBody
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
