package kr.co.mz.sns.dto.post.file;

import kr.co.mz.sns.dto.post.GenericPostDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectPostFileDto {

    private Long seq;
    private GenericPostDto genericPostDto;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
