package com.kh.demo1.web;

import com.kh.demo1.web.form.login.LoginForm;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {

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


    return "index";
  }
}
