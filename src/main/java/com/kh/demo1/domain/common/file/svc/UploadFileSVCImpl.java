package com.kh.demo1.domain.common.file.svc;

import com.kh.demo1.domain.common.file.AttachFileType;
import com.kh.demo1.domain.common.file.dao.UploadFileDAO;
import com.kh.demo1.domain.entity.UploadFile;
import com.kh.demo1.web.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service @Transactional
@RequiredArgsConstructor
public class UploadFileSVCImpl implements UploadFileSVC{

  @Value("${attach.root_dir}") // d:/attach/
  private String ROOT_DIR;

  private final UploadFileDAO uploadFileDAO;

  @Override
  public Long addFile(UploadFile uploadFile) {
    return uploadFileDAO.addFile(uploadFile);
  }

  @Override
  public void addFiles(List<UploadFile> uploadFiles) {
    uploadFileDAO.addFiles(uploadFiles);
  }

  @Override
  public Optional<List<UploadFile>> findFilesByCodeWithRid(AttachFileType attachFileType, Long rid) {
    return uploadFileDAO.findFilesByCodeWithRid(attachFileType, rid);
  }

  @Override
  public Optional<UploadFile> findFileByUploadFileId(Long uploadfileId) {
    return uploadFileDAO.findFileByUploadFileId(uploadfileId);
  }

  @Override
  public int deleteFileByUploadFildId(Long uploadfileId) {
    Optional<UploadFile> findedFile = uploadFileDAO.findFileByUploadFileId(uploadfileId);
    UploadFile file = findedFile.orElseThrow(() -> new BizException("첨부파일없음!"));
    
    //1)첨부파일 메타정보삭제
    int cnt = uploadFileDAO.deleteFileByUploadFildId(uploadfileId);
    //2)물리파일삭제
    deleteFile(AttachFileType.valueOf(file.getCode()), file.getStore_filename());

    return cnt;
  }

  @Override
  public int deleteFileByCodeWithRid(AttachFileType attachFileType, Long rid) {

    return uploadFileDAO.deleteFileByCodeWithRid(attachFileType, rid);
  }

  //물리 파일 저장 경로
  //  d:/attach/분류코드/
  @Override
  public String getFullPath(AttachFileType attachFileType) {
    StringBuffer path = new StringBuffer();
    path = path.append(ROOT_DIR).append(attachFileType.name()).append("/"); // d:/attach/분류코드/

    //경로가 없으면 생성성
    createFolder(path.toString());
    return path.toString();
  }

  //물리 저장 파일
  @Override
  public String getStoreFilename(AttachFileType attachFileType,String storeFilename) {
    StringBuffer storedFileFullPath = new StringBuffer();
    storedFileFullPath
        //  d:/attach/분류코드/
        .append(getFullPath(attachFileType))
        //  d:/attach/분류코드/xxx-yyy-xxx-uuu.png
        .append(storeFilename);
    log.info("storedFileFullPath={}",storedFileFullPath.toString());
    return storedFileFullPath.toString();
  }

  public UploadFile convert(MultipartFile mf, AttachFileType attachFileType){
    if(mf.isEmpty()) return null;
    //1) 메타정보 추출
    UploadFile uploadFile = new UploadFile();
    uploadFile.setCode(attachFileType.name()); //"F010301"
//    uploadFile.setRid(rid);
    uploadFile.setUpload_filename(mf.getOriginalFilename());

    String storeFilename = createStoreFilename(mf.getOriginalFilename());
    uploadFile.setStore_filename(storeFilename);

    uploadFile.setFsize(String.valueOf(mf.getSize()));
    uploadFile.setFtype(mf.getContentType());

    //2) 물리 파일 저장
    storeFile(mf,attachFileType,storeFilename);

    return uploadFile;
  }

  @Override
  public List<UploadFile> convert(List<MultipartFile> mfs,AttachFileType attachFileType) {
    if(mfs.size() < 1) return null;
    List<UploadFile> uploadFiles = new ArrayList<>();
    for (MultipartFile mf : mfs) {
      if(mf.isEmpty()) continue;
      uploadFiles.add(convert(mf,attachFileType));
    }
    return uploadFiles;
  }

  //물리파일 삭제
  @Override
  public boolean deleteFile(AttachFileType attachFileType ,String sfname) {

    boolean isDeleted = false;
    Path path = Paths.get(getStoreFilename(attachFileType, sfname));

    try {
      Files.delete(path);
      isDeleted =  true;
    } catch (IOException e) {
      isDeleted = false;
    }

    return isDeleted;
  }

  @Override
  public boolean deleteFiles(AttachFileType attachFileType, List<String> sfnames ) {

    boolean isDeleted = false;
    int deletedFileCount = 0;

    for(String sfname : sfnames) {
      if(deleteFile(attachFileType, sfname)) {
        deletedFileCount++;
      };
    }

    if(deletedFileCount == sfnames.size()) isDeleted = true;

    return isDeleted;
  }

  //분류코드 폴더 생성
  private void createFolder(String folder) {

    Path path = Paths.get(folder);

    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
        log.info("폴더생성={}",path.toString());
      } catch (IOException e) {
        log.info("폴더생성실패={}", e.getMessage());
      }
    }
  }

  public void storeFile(MultipartFile mf, AttachFileType attachFileType,String storeFilename) {
    String fullPath = getFullPath(attachFileType);
    Path path = Paths.get(fullPath, storeFilename);

    try {
      mf.transferTo(path);  // 디렉토리에 저장
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  //임의파일명 생성
  private String createStoreFilename(String originalFile) {
    StringBuffer storeFileName = new StringBuffer();
    storeFileName.append(UUID.randomUUID().toString()) // xxx-yyy-zzz-ttt..
        .append(".")
        .append(extractExt(originalFile)); // png,jpg
    return storeFileName.toString();
  }

  //확장자 추출
  private String extractExt(String originalFile) {
    int posOfExt =originalFile.lastIndexOf(".");
    String ext = originalFile.substring(posOfExt + 1);
    return ext;
  }

}
