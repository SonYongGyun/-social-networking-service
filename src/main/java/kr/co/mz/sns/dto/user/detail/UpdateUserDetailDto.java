package kr.co.mz.sns.dto.user.detail;

import lombok.Data;

@Data
public class UpdateUserDetailDto {

  private Long userSeq;
  private String greeting;

  public UpdateUserDetailDto userSeq(Long userSeq) {
    this.userSeq = userSeq;
    return this;
  }
}
