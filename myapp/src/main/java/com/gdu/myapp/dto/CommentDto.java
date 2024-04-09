package com.gdu.myapp.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {
  // blog 상세보기 밑에 댓글창이 있으므로 blogNo 를 BlogDto 로 받아 올 필요가 없다.
  // 또한, BlogDto 안에는 UserDto 가 있으므로 UserDto 가 중복이 있으므로 User select 할 수 없다.
  private int commentNo, depth, groupNo, blogNo, state;
  private String contents;
  private Timestamp createDt;
  private UserDto user;
}
