package kr.co.mz.sns.dto.user.detail;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailAndProfileDto {

  private Long userSeq;
  private Boolean blocked;
  private String greeting;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private List<CompleteUserProfileDto> userProfileDtoList;
//todo 나중에여기에 파일 넣어주는로직 구현해야함

  public UserDetailAndProfileDto addProfiles(List<CompleteUserProfileDto> dtoList) {
    this.userProfileDtoList = dtoList;
    return this;
  }

}
