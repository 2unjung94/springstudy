package com.gdu.prj03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.prj03.service.BoardService;

import lombok.RequiredArgsConstructor;

// @Controller 에 의해 Spring Container 에 bean 으로 BoardController 저장되어 있다.

//       @Controller  @Service  @Repository 
//view - controller - service - dao

@RequiredArgsConstructor
@Controller   // Controller 에서 사용하는 @Component
public class BoardController {

  /*************************************************** DI ***************************************************/
  /*
   * Dependency Injection
   * 1. 의존 주입
   * 2. Spring Container 에 저장된 bean 을 특정 객체에 넣어 주는 것을 의미한다.
   * 3. 방법
   *    1) 필드 주입
   *    2) 생성자 주입 (제일 많이 사용)
   *    3) setter 주입
   * 4. 사용 가능한 annotation
   *    1) @Inject
   *    2) @Resource, @Qualifier
   *    3) @Autowired (대부분 이걸 사용한다.)
   */
  
  // 1. 필드 주입 (필드 타입의 bean 을 찾는다)
  // @Autowired
  // private BoardService boardService;
  
  // 2. 생성자 주입 
  //  1) 생성자의 매개변수로 주입된다. (매개변수 타입의 bean을 찾는다)
  //  2) @Autowired 를 생략할 수 있다.
  // private BoardService boardService;
  // public BoardController(BoardService boardService) {
  //   super();
  //  this.boardService = boardService;
  // }
  
  // 3. setter 주입
  //  1) 메소드의 매개변수로 주입된다.
  //  2) @Autowired 생략할 수 없다.
  //  3) 사실 메소드명은 상관이 없다. (형식이 setter 형식일 때 가능하지 메소드명이 set어쩌구일 필요 없다)
  // private BoardService boardService;
  
  // @Autowired
  // public void setBoardService(BoardService boardService) {
  //   this.boardService = boardService;
  // }
  
  // 앞으로 사용할 한 가지 방식
  // final 필드 + 생성자 주입 (lombok의 @RequiredArgsConstructor 를 이용해서 매개변수의 null 체크를 수행함)
  // final 필드는 곧바로 초기화가 필요하지만, 생성자를 만들어 놓으면 생성자를 호출해 초기화를 시킨다.
  
  private final BoardService boardService;
  
  
  @GetMapping("/board/list.do")
  public String list(Model model) {
    model.addAttribute("boardList", boardService.getBoardList());
    return "board/list";  
  }
  
  @GetMapping("/board/detail.do")
  public String detail(int boardNo, Model model) {
    model.addAttribute("board", boardService.getBoardByNo(boardNo));
    return "board/detail";
  }
  
}
