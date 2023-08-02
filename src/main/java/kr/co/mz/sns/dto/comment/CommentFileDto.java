package kr.co.mz.sns.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

import static kr.co.mz.sns.file.FileStorageService.createCommentDirectory;
import static kr.co.mz.sns.file.FileStorageService.getFileExtension;

@Data
@NoArgsConstructor
public class CommentFileDto {

    public CommentFileDto(String uuid, String name, String path, Long size, String extension) {
        this.uuid = uuid;
        this.name = name;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }

    private Long seq;
    private Long postSeq;
    private Long commentSeq;
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentFileDto from(MultipartFile file) {
        var createdDirectory = createCommentDirectory();
        return new CommentFileDto(
                UUID.randomUUID().toString(),
                file.getOriginalFilename(),
                createdDirectory,
                file.getSize(),
                getFileExtension(file.getOriginalFilename())
        );
    }
}