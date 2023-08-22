package kr.co.mz.sns.dto.user.detail;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailAndProfileDto {

  private Long detailSeq;
  private Long userSeq;
  private Boolean blocked;
  private String greeting;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Set<CompleteUserProfileDto> userProfileDtoSet;
//todo 나중에여기에 파일 넣어주는로직 구현해야함


  public UserDetailAndProfileDto(Long detailSeq, Long userSeq, Boolean blocked, String greeting,
      LocalDateTime createdAt,
      LocalDateTime modifiedAt) {
    this.detailSeq = detailSeq;
    this.userSeq = userSeq;
    this.blocked = blocked;
    this.greeting = greeting;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public UserDetailAndProfileDto addProfiles(Set<CompleteUserProfileDto> dtoList) {
    this.userProfileDtoSet = dtoList;
    return this;
  }

}
