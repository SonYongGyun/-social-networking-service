package kr.co.mz.sns.file;

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
import java.util.UUID;
import kr.co.mz.sns.dto.post.GenericPostFileDto;
import kr.co.mz.sns.dto.user.GenericUserDetailFileDto;
import kr.co.mz.sns.exception.FileWriteException;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageService {

    public static final String BASIC_DIRECTORY = "/Users/mz01-junghunee/Documents/tutorial_directory/";


    public static String createDirectory() {
        var fileDirectory = new File(BASIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
        if (!fileDirectory.exists()) {
            var flag = fileDirectory.mkdirs();
            if (!flag) {
                System.out.println("디렉토리가 생성되지 않았습니다.");
            } else {
                System.out.println("디렉토리가 생성되었습니다.");
            }
        }
        return fileDirectory.getAbsolutePath();
    }

    public static void saveFile(List<MultipartFile> fileList, List<String> uuidList) {
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

    public static InputStream downloadFile(GenericUserDetailFileDto fileDto) {
        var fileFullPath = fileDto.getPath() + File.separator + fileDto.getUuid() + "." + fileDto.getExtension();
        try (var inputStream = new FileInputStream(fileFullPath)) {
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<GenericUserDetailFileDto> getUserFileList(List<MultipartFile> fileList) {
        var fileDtoList = new ArrayList<GenericUserDetailFileDto>();
        for (var file : fileList) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                var uuid = UUID.randomUUID().toString();
                var name = file.getOriginalFilename();
                var path = createDirectory();
                var size = file.getSize();
                var extension = getFileExtension(name);
                fileDtoList.add(new GenericUserDetailFileDto(uuid, name, path, size, extension));
            }
        }
        return fileDtoList;
    }

    public static List<GenericPostFileDto> getPostFileList(List<MultipartFile> fileList) {
        var fileDtoList = new ArrayList<GenericPostFileDto>();
        for (var file : fileList) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                var uuid = UUID.randomUUID().toString();
                var name = file.getOriginalFilename();
                var path = createDirectory();
                var size = file.getSize();
                var extension = getFileExtension(name);
                fileDtoList.add(new GenericPostFileDto(uuid, name, path, size, extension));
            }
        }
        return fileDtoList;
    }

    public static boolean delete(String filePath) {
        var file = new File(filePath);
        var flag = false;
        if (file.exists()) {
            return file.delete();
        }
        return flag;
    }

    private static String getFileExtension(String fileName) {
        var dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
