package com.gdu.prj07.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.prj07.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/contact")
@RequiredArgsConstructor
@Controller
public class ContactController {

  private final ContactService contactService;
  
  /*
   *  예전 spring 문법. 추천하지 않음 (ModelAndView 는 select 에서만 사용 가능)
   *  @GetMapping(value="/list.do")
      public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("contactList", contactService.getContactList());
        modelAndView.setViewName("contact/list");
        return modelAndView;
      }
   */
  @GetMapping(value="/list.do")
  public String list(Model model) {
    model.addAttribute("contactList", contactService.getContactList());
    return "contact/list";
  }
  
  // contactNo 의 값이 없을 때 디폴트 값으로 0을 쓰겠다는 의미
  @GetMapping(value="/detail.do")
  public String detail(@RequestParam(value="contact-no", required=false, defaultValue="0") int contactNo
                     , Model model) {
    model.addAttribute("contact", contactService.getContactByNo(contactNo));
    return "contact/detail";
  }
  
  @GetMapping(value="/write.do")
  public String write() {
    return "contact/write";
  }
  
  @PostMapping(value="/register.do")
  public void register(HttpServletRequest request, HttpServletResponse response) {
    contactService.registerContact(request, response);
  }
  
  // 주소가 같을때 메소드는 다르게 만들 수 있다.
  @GetMapping(value="/remove.do")
  public void remove1(HttpServletRequest request, HttpServletResponse response) {
    contactService.removeContact(request, response);
  }
  
  @PostMapping(value="/remove.do")
  public void remove2(HttpServletRequest request, HttpServletResponse response) {
    
    contactService.removeContact(request, response);
  }
  
  @PostMapping(value="/modify.do")
  public void modify(HttpServletRequest request, HttpServletResponse response) {
    
    contactService.modifyContact(request, response);
  }
  
  
}
