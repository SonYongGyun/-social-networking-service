package kr.co.mz.sns.dto.post;

import static kr.co.mz.sns.file.FileStorageService.createPostDirectory;
import static kr.co.mz.sns.file.FileStorageService.getFileExtension;

import java.time.LocalDateTime;
import java.util.Set;
import kr.co.mz.sns.dto.comment.CommentDto;
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
    private GenericPostDto genericPostDto;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Set<GenericPostFileDto> postFiles;
    private Set<CommentDto> comments;

    public static GenericPostFileDto from(MultipartFile file) {
        var createdDirectory = createPostDirectory();
        return GenericPostFileDto.builder()
            .name(file.getOriginalFilename())
            .path(createdDirectory)
            .size(file.getSize())
            .extension(getFileExtension(file.getOriginalFilename())).build();
    }
}

