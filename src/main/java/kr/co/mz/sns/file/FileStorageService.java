package kr.co.mz.sns.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import kr.co.mz.sns.dto.user.GenericUserDetailFileDto;
import kr.co.mz.sns.exception.FileWriteException;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageService {

    public static final String BASIC_DIRECTORY = "/Users/mz01-junghunee/Documents/tutorial_directory/";


    public static String createDirectory() {
        File fileDirectory = new File(BASIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
        if (!fileDirectory.exists()) {
            boolean flag = fileDirectory.mkdirs();
            if (!flag) {
                System.out.println("디렉토리가 생성되지 않았습니다.");
            } else {
                System.out.println("디렉토리가 생성되었습니다.");
            }
        }
        return fileDirectory.getAbsolutePath();
    }

    public static void saveFile(List<MultipartFile> fileList) {
        for (MultipartFile file : fileList) {
            if (!file.isEmpty()) {
                String filePath = createDirectory();
                String fileName = file.getOriginalFilename();
                String fileFullPath = filePath + File.separator + fileName;
                try (
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileFullPath));
                    InputStream is = new BufferedInputStream(file.getInputStream())
                ) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException ioe) {
                    throw new FileWriteException("Failed to save file", ioe);
                }
            }
        }
    }

    public static InputStream downloadFile(GenericUserDetailFileDto fileDto) {
        var fileFullPath = fileDto.getPath() + File.separator + fileDto.getName();
        try (var inputStream = new FileInputStream(fileFullPath)) {
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        boolean flag = false;
        if (file.exists()) {
            return file.delete();
        }
        return flag;
    }
}
