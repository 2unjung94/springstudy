package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BbsDto;

// implement를 안하는 인터페이스 = Marker Interface
// root-context.xml 에 bean 을 추가하지 않고 @Mapper 를 사용하자~
@Mapper
public interface BbsMapper {
  int insertBbs(BbsDto bbs);
  int getBbsCount();
  List<BbsDto> getBbsList(Map<String, Object> map);
  int updateGroupOrder(BbsDto bbs);
  int insertReply(BbsDto reply);
  int removeBbs(int bbsNo);
}
