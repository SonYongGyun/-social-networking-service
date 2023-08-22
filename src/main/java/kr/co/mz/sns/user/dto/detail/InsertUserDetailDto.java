package kr.co.mz.sns.dto.user.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertUserDetailDto {

  private Long userSeq;
  private String greeting;
}
