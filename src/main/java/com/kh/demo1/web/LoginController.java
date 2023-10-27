package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.form.login.LoginForm;
import com.kh.demo1.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  //로그인 화면
  @GetMapping("login")          // Get http://localhost:9080/login?redirectURI=/products
  public String loginForm(Model model){
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return "login/loginForm";
  }

  //로그인 처리
  @PostMapping("login")          //Post http://localhost:9080/login?redirectUrl=/products
  public String login(
//      @RequestParam("redirectUrl") String redirectUrl,
      @RequestParam(value="redirectUrl",required = false,defaultValue = "/") String redirectUrl,
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult,
      HttpServletRequest request    // http요청 메세지를 추상화한 객체
  ){
    log.info("loginForm={}", loginForm);

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "login/loginForm";
    }

    Optional<Member> optionalMember = memberSVC.findByEmail(loginForm.getEmail());
    Member member = null;
    //1) 아이디가 없는경우
    if(optionalMember.isEmpty()){
      bindingResult.reject("invalidMember",null);
    }else{
      member = optionalMember.get();
      //2)비밀번호가 다른경우
      if(!member.getPasswd().equals(loginForm.getPasswd())){
        bindingResult.reject("invalidMember",null);
      }
    }

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "login/loginForm";
    }

    //세션 생성
    //request.getSession(true) : 세션이 있으면 기존 세션 정보가져오고 없으면 새로이 세션을 생성
    //request.getSession(false) : 세션이 있으면 기존 세션 정보가져오고 없으면 세션을 생성하지 않음
    HttpSession session = request.getSession(true);
    
    //세션에 회원정보 저장
    LoginMember loginMember = new LoginMember(member.getMemberId(),member.getEmail(),member.getNickname(), member.getGubun());
    session.setAttribute("loginMember",loginMember);

    return "redirect:"+redirectUrl;   //  302 http://localhost:9080/products
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request){
    //세션정보 가져오기
    HttpSession session = request.getSession(false);
    //세션제거
    session.invalidate();

    return "redirect:/";
  }
}
