package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
  
  private final SqlSessionTemplate sqlSessionTemplate;
  
  public final static String NS = "com.gdu.prj09.mybatis.mapper.member_t.";
  
  @Override
  public int insertMember(MemberDto member) {
    return sqlSessionTemplate.insert(NS + "insertMember", member);
  }
  
  @Override
  public int insertAddress(AddressDto address) {
    return sqlSessionTemplate.insert(NS + "insertAddress", address);
  }

  @Override
  public int updateMember(MemberDto member) {
    
    return 0;
  }

  @Override
  public int deleteMember(int memeberNo) {
    
    return 0;
  }

  @Override
  public int deleteMembers(List<String> memberNoList) {

    return 0;
  }

  @Override
  public int getTotalMemeberCount() {
    
    return 0;
  }

  @Override
  public List<MemberDto> getMemberList(Map<String, Object> map) {
    
    return null;
  }

  @Override
  public MemberDto getMemberByNo(int memberNo) {
    
    return null;
  }

}
