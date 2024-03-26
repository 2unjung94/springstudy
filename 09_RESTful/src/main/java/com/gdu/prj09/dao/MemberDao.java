package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.dto.MemberDto;

public interface MemberDao {
  int insertMember(MemberDto member);
  int insertAddress(AddressDto address);
  int updateMember(MemberDto member);
  int deleteMember(int memeberNo);
  int deleteMembers(List<String> memberNoList);   // auto delete (데이터베이스에서 MEMBER_NO = 1 과 MEMBER_NO = '1' 은 둘다 같은 방식으로 동작)
  
  int getTotalMemeberCount();   // 페이징에서 total 구할 때 사용
  List<AddressDto> getMemberList(Map<String, Object> map);    // 페이징 처리를 위해 파라미터가 필요(begin+end 인 Map)
  MemberDto getMemberByNo(int memberNo);    // selectone
  
  int getTotalAddressCountByNo(int memberNo); // 어떤 회원의 주소의 개수 반환
  List<AddressDto> getAddressListByNo(Map<String, Object> map);    // 여러개 select map(begin, end, memberNo)
}
