//package kr.co.mz.sns.file;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.List;
//import kr.co.mz.sns.exception.FailedSaveFileException;
//import org.springframework.web.multipart.MultipartFile;
//
//public class FileService {
//
//    public static final String BASIC_DIRECTORY = "/Users/mz01-junghunee/Documents/tutorial_directory/";
//
//    public static File createDirectory() {
//        File fileDirectory = new File(BASIC_DIRECTORY + LocalDateTime.now().toLocalDate().toString().substring(0, 10));
//        if (!fileDirectory.exists()) {
//            boolean flag = fileDirectory.mkdirs();
//            if (!flag) {
//                System.out.println("디렉토리가 생성되지 않았습니다.");
//            } else {
//                System.out.println("디렉토리가 생성되었습니다.");
//            }
//        }
//        return fileDirectory;
//    }
//
//    private static void saveFile(List<MultipartFile> fileList) {
//
//        for(MultipartFile file : files){
//            if(!file.isEmpty()){
//
//            }
//        }
//
//        try (BufferedOutputStream bos =
//            new BufferedOutputStream(new FileOutputStream(createDirectory() + File.separator + name))
//        ) {
//            int bufferSize = 4096;
//            int startBufferIndex = 0;
//            while (startBufferIndex < profilePictureData.length) {
//                int bufferLen = Math.min(bufferSize, profilePictureData.length - startBufferIndex);
//                bos.write(profilePictureData, startBufferIndex, bufferLen);
//                startBufferIndex += bufferLen;
//            }
//        } catch (IOException ioe) {
//            throw new FailedSaveFileException("Failed to save file", ioe);
//        }
//    }
//
//    public static boolean delete(String filePath) {
//        File file = new File(filePath);
//        boolean flag = false;
//        if (file.exists()) {
//            return file.delete();
//        }
//        return flag;
//    }
//}
