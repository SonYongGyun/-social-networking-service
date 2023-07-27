package kr.co.mz.sns.service.user;

import static kr.co.mz.sns.file.FileConstants.SALVE_LOCAL_DERICTORY;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.user.GenericUserProfileDto;
import kr.co.mz.sns.entity.user.UserProfileEntity;
import kr.co.mz.sns.exception.FileWriteException;
import kr.co.mz.sns.repository.user.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public List<GenericUserProfileDto> insert(List<MultipartFile> files) {
    if (files.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    var requiredUuids = getUuidList(files.size());
    var dtos = convertToDtos(files, requiredUuids);
    this.saveIntoLocal(files, requiredUuids);
    return dtos.stream()
        .map(dto -> userProfileRepository.save(modelMapper.map(dto, UserProfileEntity.class)))
        .map(entity -> modelMapper.map(entity, GenericUserProfileDto.class))
        .toList();
  }

  @Transactional
  public Set<GenericUserProfileDto> findAll(Long userSeq) {
    return userProfileRepository.findAllByUserSeq(userSeq).stream()
        .map(entity -> modelMapper.map(entity, GenericUserProfileDto.class))
        .collect(Collectors.toSet());
  }

  @Transactional
  public int delete(Long fileSeq) {
    return userProfileRepository.deleteBySeq(fileSeq);
  }

  private String createDirectory() {
    var fileDirectory = new File(SALVE_LOCAL_DERICTORY + LocalDateTime.now().toLocalDate().toString());
    if (!fileDirectory.mkdirs()) {
      System.out.println("경로가 존재합니다.");
    }
    return fileDirectory.getAbsolutePath();
  }

  private void saveIntoLocal(List<MultipartFile> fileList, List<String> uuidList) {
    var index = 0;
    var uuidArray = uuidList.toArray();
    for (var file : fileList) {
      if (!file.isEmpty()) {
        var directory = createDirectory();
        var targetFile = directory + File.separator + uuidArray[index] + "." + getFileExtension(
            Objects.requireNonNull(file.getOriginalFilename()));
        try (
            var bis = new BufferedInputStream(file.getInputStream());
            var bos = new BufferedOutputStream(new FileOutputStream(targetFile))
        ) {
          var buffer = new byte[4096];
          int bytesRead;//한번에읽은바이트 수
          while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);// 버퍼의, 0(처음)부터, 읽은만큼
          }
        } catch (IOException ioe) {
          throw new FileWriteException("Failed to save file", ioe);
        }
      }
      index++;
    }
  }


  private List<GenericUserProfileDto> convertToDtos(List<MultipartFile> fileList, List<String> uuidList) {
    var fileDtoList = new ArrayList<GenericUserProfileDto>();
    var index = 0;
    var uuidArray = uuidList.toArray(new String[0]);
    for (var file : fileList) {
      if (!file.isEmpty() && file.getOriginalFilename() != null) {
        var uuid = uuidArray[index];
        var name = file.getOriginalFilename();
        var path = createDirectory();
        var size = file.getSize();
        var extension = getFileExtension(name);
        fileDtoList.add(new GenericUserProfileDto(uuid, name, path, size, extension));
        index++;

      }
    }
    return fileDtoList;
  }


  private String getFileExtension(String fileName) {
    var dotIndex = fileName.lastIndexOf(".");
    if (dotIndex > 0) {
      return fileName.substring(dotIndex + 1);
    }
    return "text";
  }

  private List<String> getUuidList(int size) {
    var uuidList = new ArrayList<String>();
    for (int i = 0; i < size; i++) {
      uuidList.add(UUID.randomUUID().toString());
    }
    return uuidList;
  }

}
