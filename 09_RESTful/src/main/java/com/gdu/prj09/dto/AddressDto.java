package com.gdu.prj09.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

/*
 *        Member     :       Address
 *          1        :          M
 */

/*
 * Address : zone, addr, detail, extra, MemberDto memberDto(int memberNo 대신) 
 */
public class AddressDto {
  private int addressNo;
  private String zonecode;
  private String address;
  private String detailAddress;
  private String extraAddress;
  private MemberDto member;
}
