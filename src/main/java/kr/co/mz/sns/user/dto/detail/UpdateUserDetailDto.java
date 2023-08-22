package kr.co.mz.sns.dto.user.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDetailDto {

  private Long userSeq;
  private String greeting;

  public UpdateUserDetailDto userSeq(Long userSeq) {
    this.userSeq = userSeq;
    return this;
  }
}
