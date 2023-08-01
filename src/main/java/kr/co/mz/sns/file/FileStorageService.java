package kr.co.mz.sns.file;

import static kr.co.mz.sns.file.FileConstants.SALVE_LOCAL_PUBLIC_DIRECTORY;

import jakarta.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import kr.co.mz.sns.dto.post.GenericPostDto;
import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
import kr.co.mz.sns.exception.FileWriteException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    public static final String BASIC_DIRECTORY = "/Users/mz01-junghunee/Documents/tutorial_directory/";


    public static String createDirectory() {
        var fileDirectory = new File(
            SALVE_LOCAL_PUBLIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
        if (!fileDirectory.mkdirs()) {
            System.out.println("경로가 존재합니다.");
        }
        return fileDirectory.getAbsolutePath();
    }

    public static String getFileExtension(@Nullable String fileName) {
        if (fileName == null) {
            return "";
        }
        var dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public void saveFile(List<MultipartFile> fileList, GenericPostDto insertedPostDto) {
        var uuidList = insertedPostDto.getPostFiles().stream()
            .map(fileDto -> fileDto.getSeq() + "_" + fileDto.getName() + "_" + fileDto.getSeq() + "."
                + fileDto.getExtension())
            .toList();

        var index = 0;
        var uuidArray = uuidList.toArray();
        for (var file : fileList) {
            if (!file.isEmpty()) {
                var filePath = createDirectory();
                var fileFullPath = filePath + File.separator + uuidArray[index];
                try (
                    var bos = new BufferedOutputStream(new FileOutputStream(fileFullPath));
                    var is = new BufferedInputStream(file.getInputStream())
                ) {
                    var buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException ioe) {
                    throw new FileWriteException("Failed to save file", ioe);
                }
            }
            index++;
        }
    }

    public InputStream downloadFile(CompleteUserProfileDto fileDto) {
        var fileFullPath = fileDto.getPath() + File.separator + fileDto.getUuid() + "." + fileDto.getExtension();
        try (var inputStream = new FileInputStream(fileFullPath)) {
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CompleteUserProfileDto> getUserFileList(List<MultipartFile> fileList) {
        var fileDtoList = new ArrayList<CompleteUserProfileDto>();
        for (var file : fileList) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                var uuid = UUID.randomUUID().toString();
                var name = file.getOriginalFilename();
                var path = createDirectory();
                var size = file.getSize();
                var extension = getFileExtension(name);
                fileDtoList.add(new CompleteUserProfileDto(uuid, name, path, size, extension));
            }
        }
        return fileDtoList;
    }

    public List<String> getByteArrayList(List<MultipartFile> fileList) {
        var byteArrayList = new ArrayList<String>();
        for (var file : fileList) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                try {
                    byte[] bytes = file.getInputStream().readAllBytes();
                    byteArrayList.add(Base64.getEncoder().encodeToString(bytes));
                } catch (IOException e) {
                    throw new FileWriteException("Failed file write", e);
                }
            }
        }
//        try {
//            return new ObjectMapper().writeValueAsString(byteArrayList);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        return byteArrayList;
    }

//    public static List<GenericPostFileDto> getPostFileList(List<MultipartFile> fileList) {
//        return fileList.stream()
//            .filter(file -> !file.isEmpty() && file.getOriginalFilename() != null)
//            .map(GenericPostFileDto::from)
//            .toList();

//        var fileDtoList = new ArrayList<GenericPostFileDto>();
//        for (var file : fileList) {
//            if (!file.isEmpty() && file.getOriginalFilename() != null) {
//                fileDtoList.add(GenericPostFileDto.from(file));
//            }
//        }
//        return fileDtoList;
//    }

    public <T> List<T> convertTo(List<MultipartFile> fileList, Function<MultipartFile, T> function) {
        if (fileList.isEmpty()) {
            return List.of();
        }

        return fileList.stream()
            .filter(file -> !file.isEmpty() && file.getOriginalFilename() != null)
            .map(function)
            .toList();
    }

    public boolean delete(String filePath) {
        var file = new File(filePath);
        var flag = false;
        if (file.exists()) {
            return file.delete();
        }
        return flag;
    }
}
