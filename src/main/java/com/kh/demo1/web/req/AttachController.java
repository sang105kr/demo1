package com.kh.demo1.web.req;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/attach")
public class AttachController {

  private static String UPLOADED_FOLDER = "d:/attach/";

  @ResponseBody
  @PostMapping("/file")
  public String file(@RequestParam("file") MultipartFile multipartFile) {

    if (multipartFile.isEmpty()) {
      return "첨부된 파일이 없습니다.";
    }

    String originalFilename = multipartFile.getOriginalFilename();
    long size = multipartFile.getSize();
    String contentType = multipartFile.getContentType();

    log.info("file={}", multipartFile);
    log.info("{},{},{}", originalFilename, size, contentType);

    try {
      // 첨부파일 읽기
      byte[] bytes = multipartFile.getBytes();
      // 업로드할 경로 지정
      Path path = Paths.get(UPLOADED_FOLDER + multipartFile.getOriginalFilename());
      // 경로에 파일저장
      multipartFile.transferTo(path);

      return "첨부 완료";

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "ok";
  }

  @ResponseBody
  @PostMapping("/files")
  public String files(@RequestParam("files") List<MultipartFile> multipartFiles){

    if (multipartFiles.size() == 0) {
      return "첨부된 파일이 없습니다.";
    }


    for (MultipartFile multipartFile : multipartFiles) {

      String originalFilename = multipartFile.getOriginalFilename();
      long size = multipartFile.getSize();
      String contentType = multipartFile.getContentType();

      log.info("{},{},{}", originalFilename,size,contentType);

      try {
        // 첨부파일 읽기
        byte[] bytes = multipartFile.getBytes();
        // 업로드할 경로 지정
        Path path = Paths.get(UPLOADED_FOLDER + multipartFile.getOriginalFilename());
        // 경로에 파일저장
        multipartFile.transferTo(path);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    log.info("files={}", multipartFiles);
    return "첨부완료";
  }

  //다운로드
  @ResponseBody
  @GetMapping("/file/down/{filename:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String filename){

    Path path = Paths.get(UPLOADED_FOLDER).resolve(filename).normalize();
    try {

      Resource resource = new UrlResource(path.toUri());
      if(resource.exists()){
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attach;filename=\"" + resource.getFilename() + "\"")
            .body(resource);

      }else {
        return ResponseEntity.notFound().build();
      }

    } catch (MalformedURLException e) {
      return ResponseEntity.notFound().build();
    }
  }

  //이미지 보기
  @ResponseBody
  @GetMapping("/file/view/{filename:.+}")
  public ResponseEntity<Resource> fileView(@PathVariable String filename){

    Path path = Paths.get(UPLOADED_FOLDER).resolve(filename).normalize();
    try {

      Resource resource = new UrlResource(path.toUri());
      if(resource.exists()){
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resource.getFilename() + "\"")
            .body(resource);

      }else {
        return ResponseEntity.notFound().build();
      }

    } catch (MalformedURLException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
