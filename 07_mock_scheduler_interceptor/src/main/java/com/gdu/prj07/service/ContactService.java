package com.gdu.prj07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.prj07.dto.ContactDto;

public interface ContactService {
  
  void registerContact(HttpServletRequest request, HttpServletResponse response);   // service 가 view 에게 직접 응답을 하기 위해서 void 반환과 response 파라미터 처리
  void modifyContact(HttpServletRequest request, HttpServletResponse response);     // service 가 view 에게 직접 응답을 하기 위해서 void 반환과 response 파라미터 처리
  void removeContact(HttpServletRequest request, HttpServletResponse response);     // service 가 view 에게 직접 응답을 하기 위해서 void 반환과 response 파라미터 처리
  List<ContactDto> getContactList();
  ContactDto getContactByNo(int contactNo);
  void txTest();
  
}
