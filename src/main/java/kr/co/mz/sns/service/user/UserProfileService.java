package kr.co.mz.sns.service.user;

import static kr.co.mz.sns.service.file.FileConstants.SALVE_LOCAL_DIRECTORY;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
import kr.co.mz.sns.entity.user.UserProfileEntity;
import kr.co.mz.sns.exception.FileWriteException;
import kr.co.mz.sns.exception.NotFoundException;
import kr.co.mz.sns.repository.user.UserDetailRepository;
import kr.co.mz.sns.repository.user.UserProfileRepository;
import kr.co.mz.sns.util.CurrentUserInfo;
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
  private final CurrentUserInfo currentUserInfo;
  private final UserDetailRepository userDetailRepository;

  @Transactional
  public List<CompleteUserProfileDto> insert(List<MultipartFile> files) {
    if (files.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    var requiredUuids = getUuidList(files.size());
    var dtos = convertToDtos(files, requiredUuids);
    this.saveIntoLocal(files, requiredUuids);
    var userDetail = userDetailRepository
        .findByUserEntity_Seq(currentUserInfo.getSeq())
        .orElseThrow(() -> new NotFoundException("없는 사용자입니다."));
    return dtos
        .stream()
        .map(dto -> modelMapper
            .map(
                dto,
                UserProfileEntity.class
            )
            .userDetailEntity(userDetail))
        .map(userProfileRepository::save)
        .map(entity -> modelMapper.map(entity, CompleteUserProfileDto.class))
        .toList();
  }

  @Transactional
  public Set<CompleteUserProfileDto> findAll(Long userSeq) {
    return userProfileRepository.findAllByUserDetailEntity_UserEntity_Seq(userSeq)
        .stream()
        .map(entity -> modelMapper.map(entity, CompleteUserProfileDto.class))
        .collect(Collectors.toSet());
  }

  @Transactional
  public CompleteUserProfileDto delete(Long fileSeq) {
    return modelMapper
        .map(userProfileRepository.deleteBySeq(fileSeq), CompleteUserProfileDto.class);
  }

  @Transactional
  public List<Long> deleteAll(Long userSeq) {
    var profileSeqList = userProfileRepository.findAllUserProfileSeqsByUserEntity_Seq(userSeq);
    userProfileRepository
        .deleteAllByUserSeq(
            profileSeqList
        );
    return profileSeqList;
  }

  public InputStream downloadFile(CompleteUserProfileDto profileDto) {
    var fileFullPath = profileDto.getPath() + File.separator + profileDto.getUuid() + "." + profileDto.getExtension();
    try (var inputStream = new FileInputStream(fileFullPath)) {
      return inputStream;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String createDirectory() {
    var fileDirectory = new File(SALVE_LOCAL_DIRECTORY + LocalDateTime.now().toLocalDate().toString());
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


  private List<CompleteUserProfileDto> convertToDtos(List<MultipartFile> fileList, List<String> uuidList) {
    var fileDtoList = new ArrayList<CompleteUserProfileDto>();
    var index = 0;
    var uuidArray = uuidList.toArray(new String[0]);
    for (var file : fileList) {
      if (!file.isEmpty() && file.getOriginalFilename() != null) {
        var uuid = uuidArray[index];
        var name = file.getOriginalFilename();
        var path = createDirectory();
        var size = file.getSize();
        var extension = getFileExtension(name);
        fileDtoList.add(new CompleteUserProfileDto(uuid, name, path, size, extension));
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
