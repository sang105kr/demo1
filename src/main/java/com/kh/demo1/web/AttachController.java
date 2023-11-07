package com.kh.demo1.web;

import com.kh.demo1.domain.common.file.AttachFileType;
import com.kh.demo1.domain.common.file.svc.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {

  private final UploadFileSVC uploadFileSVC;

  //다운로드
  @GetMapping("/down/{code}/{storeFilename:.+}")
  public ResponseEntity<Resource> downloadFile(
      @PathVariable AttachFileType code,
      @PathVariable String storeFilename) {

    Path path = Path.of(uploadFileSVC.getStoreFilename(code, storeFilename));
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
  @GetMapping("/view/{code}/{storeFilename:.+}")
  public ResponseEntity<Resource> fileView(
      @PathVariable AttachFileType code,
      @PathVariable String storeFilename){

    Path path = Path.of(uploadFileSVC.getStoreFilename(code, storeFilename));
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
