package kr.co.mz.sns.dto.user.detail;

import java.util.Set;
import lombok.Data;

@Data
public class UpdateUserDetailDto {

  private Long userSeq;
  private String greeting;
  private Set<CompleteUserProfileDto> userDetailFileDtoSet;
}
