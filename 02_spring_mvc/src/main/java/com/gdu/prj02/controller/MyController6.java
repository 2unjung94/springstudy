package com.gdu.prj02.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.gdu.prj02.dto.UserDto;

/* Session 정보를 꺼내서 저장하기 위주로 공부(login, mypage 신경 쓰지 말 것) */
@SessionAttributes(names="user")  // Model 에 user 가 저장되면 session 에 같은 값을 저장한다.
@Controller
public class MyController6 {

  // HttpSession 을 바로 파라미터로 넣을 수 있다.
  // @GetMapping("/user/mypage.do")
  public String mypage1(HttpSession session, Model model) {
    
    // getAttribute 의 결과값들은 다 object 이니까 (UserDto)로 형변환 해줘야 함
    // session 에 저장된 user 정보
    UserDto user = (UserDto) session.getAttribute("user");
    
    // model 에 user 정보 저장
    model.addAttribute("user", user);
    
    // user/mypage.jsp 로 forward
    return "user/mypage";
    
  }
  
  @GetMapping("/user/login1.do")
  public String login1(HttpServletRequest request, Model model) {
    
    // HttpSession 구하기
    HttpSession session = request.getSession();
    
    // session 에 저장할 객체
    UserDto user = new UserDto(1, "min@naver.com");
    
    // session 에 객체 저장하기
    session.setAttribute("user", user);
    
    // 메인 페이지로 이동
    // return "index"; // forward
    return "redirect:/main.do"; // redirect 권장 (로그인이 끝나면 새로운 요청으로 페이지 여는 것)
    
  }
  
  @GetMapping("/user/logout1.do")
  public String logout1(HttpServletRequest request) {
    // HttpSession 구하기
    HttpSession session = request.getSession();
    
    // session 의 모든 정보 지우기 (session 초기화)
    session.invalidate();
    
    return "redirect:/main.do"; // redirect 권장 (로그인이 끝나면 새로운 요청으로 페이지 여는 것)
  }
  
  @GetMapping("/user/login2.do")
  public String login2(Model model) {
    
    // model 에 저장할 객체
    UserDto user = new UserDto(1, "min@naver.com");
    
    // model 에 객체 저장하기 (@SessionAttributes 에 의해서 session 에도 저장된다.)
    model.addAttribute("user", user);
    
    // 메인 페이지로 이동
    return "redirect:/main.do";
  }

  @GetMapping("/user/logout2.do")
  public String logout2(SessionStatus sessionStatus) {
    
    // session attribute 삭제를 위해 세션 완료 처리
    sessionStatus.setComplete();
    
    // 메인 페이지로 이동
    return "redirect:/main.do";
  }

  @GetMapping("/user/mypage.do")
  public String mypage2(@SessionAttribute(name="user") UserDto user) {    // session attribute 중 user 를 UserDto 에 저장하시오.
   
    // user/mypage.jsp 로 forward
    return "user/mypage";
  }

}
