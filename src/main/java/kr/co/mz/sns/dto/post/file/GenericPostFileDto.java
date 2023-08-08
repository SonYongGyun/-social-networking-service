package kr.co.mz.sns.dto.post.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static kr.co.mz.sns.file.FileStorageService.createPostDirectory;
import static kr.co.mz.sns.file.FileStorageService.getFileExtension;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericPostFileDto {


    private Long seq;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private Long postSeq;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static GenericPostFileDto from(MultipartFile file) {
        var createdDirectory = createPostDirectory();
        return GenericPostFileDto.builder()
                .name(file.getOriginalFilename())
                .path(createdDirectory)
                .size(file.getSize())
                .extension(getFileExtension(file.getOriginalFilename())).build();
    }
}