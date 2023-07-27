package kr.co.mz.sns.dto.post;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
