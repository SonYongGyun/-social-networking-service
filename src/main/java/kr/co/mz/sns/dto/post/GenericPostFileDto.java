package kr.co.mz.sns.dto.post;

import static kr.co.mz.sns.file.FileStorageService.createDirectory;
import static kr.co.mz.sns.file.FileStorageService.getFileExtension;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class GenericPostFileDto {

    public GenericPostFileDto(String uuid, String name, String path, Long size, String extension) {
        this.uuid = uuid;
        this.name = name;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }

    private Long seq;
    private Long postSeq;
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static GenericPostFileDto from(MultipartFile file) {
        var createdDirectory = createDirectory();
        return new GenericPostFileDto(
            UUID.randomUUID().toString(),
            file.getOriginalFilename(),
            createdDirectory,
            file.getSize(),
            getFileExtension(file.getOriginalFilename())
        );
    }
}
