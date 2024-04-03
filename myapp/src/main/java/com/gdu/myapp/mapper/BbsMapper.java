package com.gdu.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;

// implement를 안하는 인터페이스 = Marker Interface
// root-context.xml 에 bean 을 추가하지 않고 @Mapper 를 사용하자~
@Mapper
public interface BbsMapper {
  
}
