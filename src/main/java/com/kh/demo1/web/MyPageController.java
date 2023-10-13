package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.common.MyUtil;
import com.kh.demo1.web.form.mypage.member.DetailForm;
import com.kh.demo1.web.form.mypage.member.ModifyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

  private final MemberSVC memberSVC;

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

  @GetMapping
  public String mypage(){
    return "mypage/intro";
  }

  //회원조회
  @GetMapping("/members/{email}")
  public String detail(
      @PathVariable("email") String email,
      Model model){
    log.info("email={}",email);

    Optional<Member> optionalMember = memberSVC.findByEmail(email);
    Member member = null;
    if(optionalMember.isPresent()) {
      member = optionalMember.get();
    }

    DetailForm detailForm = new DetailForm();
    detailForm.setMemberId(member.getMemberId());
    detailForm.setEmail(member.getEmail());
    detailForm.setTel(member.getTel());
    detailForm.setNickname(member.getNickname());
    detailForm.setGender(member.getGender());
    detailForm.setHobby(Arrays.asList(member.getHobby().split(",")));
    detailForm.setRegion(member.getRegion());
    detailForm.setPic(member.getPic());

    log.info("detailForm={}", detailForm);
    model.addAttribute("detailForm",detailForm);

    return "mypage/member/detailForm";
  }

  //회원수정양식
  @GetMapping("/member/{email}/edit")
  public String modifyForm(@PathVariable("email") String email,Model model){

    Optional<Member> optionalMember = memberSVC.findByEmail(email);
    Member member = null;
    if(optionalMember.isPresent()) {
      member = optionalMember.get();
    }

    ModifyForm modifyForm = new ModifyForm();
    modifyForm.setMemberId(member.getMemberId());
    modifyForm.setEmail(member.getEmail());
    modifyForm.setTel(member.getTel());
    modifyForm.setNickname(member.getNickname());
    modifyForm.setGender(member.getGender());
    modifyForm.setHobby(Arrays.asList(member.getHobby().split(",")));
    modifyForm.setRegion(member.getRegion());
    modifyForm.setPic(member.getPic());

    log.info("modifyForm={}", modifyForm);
    model.addAttribute("modifyForm",modifyForm);

    return "mypage/member/modifyForm";
  }

  //회원수정처리
  @PatchMapping("/member/{email}/edit")
  public String modify(
      @PathVariable("email") String email,
      @Valid @ModelAttribute ModifyForm modifyForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes){

    // 필드 어노테이션 기반 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "mypage/member/modifyForm";
    }

    // 필드 코드 기반 검증
    //1) 비밀번호 체크
    String savedPasswd = memberSVC.findByEmail(email).get().getPasswd();
    if(!savedPasswd.equals(modifyForm.getPasswd())) {
      bindingResult.rejectValue("passwd","member");
    }

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "mypage/member/modifyForm";
    }

    Member member = new Member();
    member.setTel(modifyForm.getTel());
    member.setNickname(modifyForm.getNickname());
    member.setGender(modifyForm.getGender());
    member.setHobby(MyUtil.listToString(modifyForm.getHobby()));
    member.setRegion(modifyForm.getRegion());

    //수정처리
    int rows = memberSVC.modify(email, member);


    redirectAttributes.addAttribute("email",email);
    return "redirect:/mypage/members/{email}";   // 302 GET http://localhost:9080/mypage/members/test1@kh.com
  }

}
