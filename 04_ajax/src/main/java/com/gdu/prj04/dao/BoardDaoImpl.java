package com.gdu.prj04.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.prj04.dto.BoardDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor   // 모든 필드값으로 생성자를 만듬. 생성자의 Autowired 는 생략이 가능하므로 결국 @AllArgsConstructor 만 작성해줘도 된다
@Repository    // bean 으로 만들라는 annotation
public class BoardDaoImpl implements BoardDao {

  // @Autowired 할 때 타입이 같다면 변수 이름으로 구분해서 가져온다.
  // setter 형식의 autowired 는 메소드의 매개변수를 자동 주입 대상으로 본다.
  
  private BoardDto board1;
  private BoardDto board2;
  private BoardDto board3;
  
  @Override
  public List<BoardDto> getBoardList() {
    
    return Arrays.asList(board1, board2, board3);
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto board = null;
    
    switch(boardNo) {
    case 1: board = board1;
      break;
    case 2: board = board2;
      break;
    case 3: board = board3;
      break;
    }
    
    return board;
  }

}
