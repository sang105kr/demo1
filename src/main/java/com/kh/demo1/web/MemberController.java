package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.form.member.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/members")       //  http://localhost:9080/members
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  //취미
  @ModelAttribute("hobbys")   // model.addAttributes("hobbys",hobbys);
  public Map<String,String> hobbys(){
    Map<String,String> hobbys = new HashMap<>();
    hobbys.put("swim","수영");
    hobbys.put("climing", "등산");
    hobbys.put("golf", "골프");
    return hobbys;
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
//    member.setHobby(joinForm.getHobby());
    member.setRegion(joinForm.getRegion());
    member.setGubun("M0101"); //일반회원
    Member joinedMember = memberSVC.join(member);

    return "index"; // view 이름
  }

}
