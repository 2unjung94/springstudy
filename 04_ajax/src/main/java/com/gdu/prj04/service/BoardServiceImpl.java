package com.gdu.prj04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.prj04.dao.BoardDao;
import com.gdu.prj04.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor    // final 필드의 주입은 null 체크가 가능한 RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardDao boardDao;
  
  @Override
  public List<BoardDto> getBoardList() {    
    return boardDao.getBoardList();
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    return boardDao.getBoardByNo(boardNo);
  }

}
