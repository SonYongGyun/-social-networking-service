package kr.co.mz.sns.dto.post;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
