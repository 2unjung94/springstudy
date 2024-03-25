package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.gdu.prj09.dto.MemberDto;

// 하나의 서비스-여러개의 Dao 가능, 1:n 의 관계다.
// 삭제는 2개의 서비스로 분리
public interface MemberService {
  
  // 총 6개의 서비스 (반환 타입은 다 동일)
  // ResponseEntity<T> : @ResponseBody 를 갖고 있는 클래스, Single page 처리를 위해 Spring 에서 만든 클래스, wrapper 같은 클래스
  // 파라미터 받는 방식
  // 1. HttpServletRequest 2. @RequestParam 3. MyPageUtils 직접 전달(setter 처리를 안해서 지금은 못함)
  
  // 연습하는 주소 체계                      이전에 했던 주소 체계
  // /prj09/members/page/1/display/20        /prj09/members?page=1&display=20
  // @PathVariable                           @RequestParam
  
  // 전체조회: /members -> getMembers(int page, int display) -> getTotalMemberCount() + getMemberList(Map map)
  ResponseEntity<Map<String, Object>> getMembers(int page, int display);  // display, page 두가지를 매개변수로 가짐
  
  // 상세조회: /members/1
  ResponseEntity<MemberDto> getMemberByNo(int memberNo); 
  
  // 등록: 이번에는 unique 처리 안함(1. 정상 처리 2. Exception 처리(중복시 catch block 에서 응답))
  ResponseEntity<Map<String, Object>> registerMember(Map<String, Object> map, HttpServletResponse response); // map을 전달해야 jackson이 json 데이터를 만들어준다.
  
  // 수정
  ResponseEntity<Map<String, Object>> modifyMember(MemberDto member);   // 이메일은 수정하지 않을 거라서 response(중복처리) 필요 없다.
  
  // 삭제: /members/1 (상세조회와 같은 주소를 사용하지만 method를 사용해서 요청을 구분한다)
  ResponseEntity<Map<String, Object>> removeMember(int memberNo);
  
  // 전체 삭제: /members/1,2 (1,2 = String)
  ResponseEntity<Map<String, Object>> removeMembers(String memberNoList);
  
}
