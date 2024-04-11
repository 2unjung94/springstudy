package com.gdu.myapp.service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.AttachDto;
import com.gdu.myapp.dto.UploadDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UploadMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;
  private final MyPageUtils myPageUtils;
  private final MyFileUtils myFileUtils;
  
  @Override
  public boolean registerUpload(MultipartHttpServletRequest multipartRequest) {
    
    // UPLOAD_T 테이블에 추가하기
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    UploadDto upload = UploadDto.builder()
                            .title(title)
                            .contents(contents)
                            .user(user)
                          .build();
    
    System.out.println("INSERT 이전 : " + upload.getUploadNo());  // uploadNo 없음
    int insertUploadCount = uploadMapper.insertUpload(upload);
    System.out.println("INSERT 이후 : " + upload.getUploadNo());  // uploadNo 있음 (<selectKey> 동작에 의해)
    
    // 첨부 파일 처리하기
    // write.jsp 에서 첨부파일을 multiple 로 설정하였으므로 getFiles로 받는다.
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 첨부 파일이 없는 경우 : [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]]
    // 첨부 파일이 있는 경우 : [MultipartFile[field="files", filename=404.jpg, contentType=image/jpeg, size=63891]]
    // 첨부가 있던 없던 files의 첫번째 요소는 무조건 있다
    // System.out.println(files);
    
    int insertAttachCount;
    if(files.get(0).getSize() == 0) {
      insertAttachCount = 1;    // 첨부가 없어도 files.size() 는 1 이다.
    } else {
      insertAttachCount = 0;
    }
    
    // stream() : 배열이나 리스트를 stream 으로 만들어서 pipeline 처리
    for (MultipartFile multipartFile : files) {
      if(multipartFile != null && !multipartFile.isEmpty()) {
        String uploadPath = myFileUtils.getUploadPath();
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        try {
          multipartFile.transferTo(file);
          
          // 썸네일 작성
          // img content type = image/png, image/jpeg, ...
          // File <-> Path 그냥 타입만 바꿔서 사용하면 된다
          String contentType = Files.probeContentType(file.toPath());
          int hasThumbnail = contentType != null && contentType.startsWith("image") ? 1 : 0;
          
          // 이미지의 썸네일 만들기
          if(hasThumbnail == 1) {  
            File thumbnail = new File(dir, "s_" + filesystemName);
            Thumbnails.of(file)           // 원본 이미지 파일
                      .size(96, 64)       // 가로 96px, 세로 64px
                      .toFile(thumbnail); // 썸네일 이미지 파일
          }
          
          // ATTACH_T 테이블에 추가하기
          AttachDto attach = AttachDto.builder()
                                  .uploadPath(uploadPath)
                                  .filesystemName(filesystemName)
                                  .originalName(originalFilename)
                                  .hasThumbnail(hasThumbnail)
                                  .uploadNo(upload.getUploadNo())
                                .build();
          
          insertAttachCount += uploadMapper.insertAttach(attach);
                    
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }  // if
    
    }  // for
    
    // 첨부개수와 files 리스트의 길이와 같아야 함
    return (insertUploadCount == 1) && (insertAttachCount == files.size()) ? true : false;
  }

  @Override
  public void loadUploadList(Model model) {
    
    /*
     * interface UploadService {
     *   void execute(Model model);
     * }
     * 
     * class UploadRegisterService implements UploadService {
     *   @Override
     *   void excute(Model model){
     *   
     *   }
     * }
     * 
     * class UploadListService implements UploadSerivce {
     *   @Override
     *   void excute(Model model){
     *   
     *   }
     * }
     */
    
    // 최신 버전인 model.getAttribute("") 역할을 하던 것이 asMap. model 을 map 으로 바꾸고 map 의 데이터를 꺼내서 쓴다.
    Map<String, Object> modelMap = model.asMap();
    HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
    
    int total = uploadMapper.getUploadCount();
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    myPageUtils.setPaging(total, display, page);
    
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd()
                                   , "sort", sort);
    /*
     * total = 100, display = 20 
     * 
     * beginNo : 각 페이지의 시작 번호
     * page    beginNo
     * 1       100
     * 2       80
     * 3       60
     * 4       40
     * 5       20
     */
    
    model.addAttribute("beginNo", total - (page - 1) * display);
    model.addAttribute("uploadList", uploadMapper.getUploadList(map));
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/upload/list.do", sort, display));
    model.addAttribute("display", display);
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);

  }
  @Override
  public void loadUploadByNo(int uploadNo, Model model) {
    
    model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
  }

}
