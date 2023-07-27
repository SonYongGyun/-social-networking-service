package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserDetailDto {

  private Long userSeq;
  private Boolean blocked;
  private String greeting;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Set<GenericUserDetailFileDto> userDetailFileDtoSet;
//todo 나중에여기에 파일 넣어주는로직 구현해야함
}
