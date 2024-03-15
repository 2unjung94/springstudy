package com.gdu.prj04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
    
  @GetMapping(value= {"/", "/main.do"})
  public String welcome() {

    return "index";
  }
  
  @GetMapping(value="/exercise1.do")    // exercise1.jsp 로 forward 함
  public void exercise1() { }
  
  @GetMapping(value="/exercise2.do")
  public String exercise2() {
    return "exercise2";
  }
  
  @GetMapping(value="/exercise3.do")
  public String exercise3() {
    return "exercise3";
  }
  
}
