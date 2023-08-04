package kr.co.mz.sns.dto.post;

import static kr.co.mz.sns.file.FileStorageService.createPostDirectory;
import static kr.co.mz.sns.file.FileStorageService.getFileExtension;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
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

