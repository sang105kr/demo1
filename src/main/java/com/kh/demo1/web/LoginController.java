package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.form.login.LoginForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  //로그인 화면
  @GetMapping("login")
  public String loginForm(Model model){
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return "login/loginForm";
  }

  //로그인 처리
  @PostMapping("login")
  public String login(
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult){
    log.info("loginForm={}", loginForm);

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "login/loginForm";
    }

    Optional<Member> optionalMember = memberSVC.findByEmail(loginForm.getEmail());
    //1) 아이디가 없는경우
    if(optionalMember.isEmpty()){
      bindingResult.reject("invalidMember",null);
    }else{
      Member member = optionalMember.get();
      //2)비밀번호가 다른경우
      if(!member.getPasswd().equals(loginForm.getPasswd())){
        bindingResult.reject("invalidMember",null);
      }
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "login/loginForm";
    }

    return "index";
  }
}
