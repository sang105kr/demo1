package com.kh.demo1.web.req;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/attach")
public class AttachController {

  @ResponseBody
  @PutMapping("/file")
  public String file(@RequestParam("file") MultipartFile multipartFile){

    String originalFilename = multipartFile.getOriginalFilename();
    long size = multipartFile.getSize();
    String contentType = multipartFile.getContentType();

    log.info("file={}", multipartFile);
    log.info("{},{},{}", originalFilename,size,contentType);
    return "ok";
  }

  @ResponseBody
  @PutMapping("/files")
  public String files(@RequestParam("files") List<MultipartFile> multipartFiles){

    for (MultipartFile multipartFile : multipartFiles) {

      String originalFilename = multipartFile.getOriginalFilename();
      long size = multipartFile.getSize();
      String contentType = multipartFile.getContentType();

      log.info("{},{},{}", originalFilename,size,contentType);
    }

    log.info("files={}", multipartFiles);
    return "ok";
  }
}
