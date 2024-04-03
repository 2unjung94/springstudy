package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface BbsService {
  // 매개변수를 HttpServletRequest 로 통일하고 request 에 Model 을 저장해서 할 수 있다.
  // Model 로 통일할 수 있다. (Model 의 기원은 Map 이다)
  int registerBbs(HttpServletRequest request);
  void loadBbsList(HttpServletRequest request, Model model);
  int registerReply(HttpServletRequest request);
  int removeBbs(int bbsNo);
  void loadBbsSearchList(HttpServletRequest request, Model model);
}