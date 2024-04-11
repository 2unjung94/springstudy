package com.gdu.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadDto {
  private int uploadNo;
  private String title, contents, createDt, modifyDt;
  private UserDto user;
  private int attachCount;  // select 의 조회 결과를 저장할 때 사용하는 변수(이름 맞춰줘야 한다)
}