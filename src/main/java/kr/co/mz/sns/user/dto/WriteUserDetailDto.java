package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WriteUserDetailDto {

  private String name;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
