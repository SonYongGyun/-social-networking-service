package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GenericUserDetailFileDto {

    public GenericUserDetailFileDto(String uuid, String name, String path, Long size, String extension) {
        this.uuid = uuid;
        this.name = name;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }

    public GenericUserDetailFileDto(String name) {
        this.name = name;
    }

    private Long seq;
    private Long userSeq;
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
