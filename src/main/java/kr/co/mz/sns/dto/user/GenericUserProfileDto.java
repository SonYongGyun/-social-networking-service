package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericUserProfileDto {

  private Long seq;
  private Long userSeq;
  private String uuid;
  private String name;
  private String path;
  private Long size;
  private String extension;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public GenericUserProfileDto(String uuid, String name, String path, Long size, String extension) {
    this.uuid = uuid;
    this.name = name;
    this.path = path;
    this.size = size;
    this.extension = extension;
  }

  public GenericUserProfileDto(String name) {
    this.name = name;
  }

}
