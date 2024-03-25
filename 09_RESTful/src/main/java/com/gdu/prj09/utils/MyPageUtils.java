package com.gdu.prj09.utils;

public class MyPageUtils {
  
  private int total;    // 전제 멤버 개수
  private int display;  // 한 페이지에 표시하는 리스트 개수
  private int page;     // begin, end 구할 때 필요함(total, display, page - service 로 부터 받아옴)
  private int begin;
  private int end;
  
  private int pagePerBlock = 10;    // 1,2,3,4,5,6,7,8,9,10 까지 보여줌
  private int totalPage;
  private int beginPage;
  private int endPage;
  
  public void setPaging(int total, int display, int page) {
    this.total = total;
    this.display = display;
    this.page = page;
    
    // n페이지(begin - end)
    // 1페이지(1 - 20), 2페이지(21 - 40), 3페이지(41 - 60), ...
    begin = (page - 1) * display + 1;
    end = begin + display - 1;
    
    // total      display       totalPage
    // 1000       20            1000 / 20 = 50.0  = 50
    // 1001       20            1001.0 / 20.0 = 50.x  = 51 (올림처리)
    totalPage = (int)Math.ceil((double)total / display);   // 하나만 double 이 되면 나머지는 자동으로 double 이 된다. 
    
    // 1block(1 2 3 4 5 6 7 8 9 10) / 2block(11 12 13 14 15 16 17 18 19 20) / ...
    beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1 ;
    endPage = Math.min(totalPage, beginPage + pagePerBlock -1);   // 51일 때 51-60이면 안되니까 둘 중 작은값을 쓴다.
    
  }
  
  // SinglePage 처리
  // insert, delete, select, update 등 - ajax 처리
  // js 함수(아래와 같이 작성)
  // ajax - function getList(){}
  // 페이지 숫자 a 태그 처리 - function getPaging(){} : <a href="javascript:getPaging()">
  
  public String getAsyncPaging() {
    
    StringBuilder builder = new StringBuilder();
    
    // <
    
    
    // 1 2 3 4 5 6 7 8 9 10
    
    
    // >
    
    
    
    return builder.toString();
    
  }
  
}
