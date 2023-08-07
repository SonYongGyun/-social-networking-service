package kr.co.mz.sns.dto.user.detail;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

  public CompleteUserProfileDto(String name) {
    this.name = name;
  }

  public CompleteUserProfileDto userSeq(Long seq) {
    this.userSeq = seq;
    return this;
  }
}
