package com.kh.demo1.web;

import com.kh.demo1.domain.dao.entity.Member;
import com.kh.demo1.domain.svc.MemberSVC;
import com.kh.demo1.web.form.mypage.member.DetailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
}
