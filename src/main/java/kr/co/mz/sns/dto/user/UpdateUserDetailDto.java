package kr.co.mz.sns.dto.user;

import java.util.Set;
import lombok.Data;

@Data
public class UpdateUserDetailDto {

  private Long userSeq;
  private String greeting;
  private Set<GenericUserDetailFileDto> userDetailFileDtoSet;
}
