package com.kh.demo1.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {
  @Setter@Getter
  @AllArgsConstructor
  static class Person {
    private String name;
    private int age;
  }
  @ResponseBody  //응답 메세지 바디에 직접 쓰기
  @GetMapping("/t1")
  public String t1(){
    return "hi";
  }

  @ResponseBody
  @GetMapping("/t2")
  public Person t2(){
    Person p1 = new Person("홍길동", 30);
    return p1;
  }

  @ResponseBody
  @GetMapping("t3")
  public List<Person> t3(){
    List<Person> persons = Arrays.asList(
        new Person("홍길동1",10),
        new Person("홍길동2", 20),
        new Person("홍길동3", 30));
    return persons;
  }

  @ResponseBody
  @GetMapping("t4")
  public Set<Person> t4(){
    Set<Person> persons = new LinkedHashSet<>();
    persons.add( new Person("홍길동1",10));
    persons.add( new Person("홍길동2",20));
    persons.add( new Person("홍길동3",30));
    return persons;
  }

  @ResponseBody
  @GetMapping("t5")
  public Map<String,Person> t5(){
    Map<String,Person> persons = new LinkedHashMap<>();
    persons.put( "1",new Person("홍길동1",10));
    persons.put( "2",new Person("홍길동2",20));
    persons.put( "3",new Person("홍길동3",30));
    return persons;
  }
}
