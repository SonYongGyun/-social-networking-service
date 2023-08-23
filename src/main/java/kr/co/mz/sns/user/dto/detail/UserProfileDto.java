package kr.co.mz.sns.user.dto.detail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CompleteUserProfileDto {

    private Long seq;
    private Long userSeq;
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CompleteUserProfileDto(String uuid, String name, String path, Long size, String extension) {
        this.uuid = uuid;
        this.name = name;
        this.path = path;
        this.size = size;
        this.extension = extension;
    }

    public CompleteUserProfileDto(Long seq, Long userSeq, String uuid, String name, String path, Long size,
                                  String extension, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.uuid = uuid;
        this.name = name;
        this.path = path;
        this.size = size;
        this.extension = extension;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public CompleteUserProfileDto(String name) {
        this.name = name;
    }

    public CompleteUserProfileDto userSeq(Long seq) {
        this.userSeq = seq;
        return this;
    }
}
