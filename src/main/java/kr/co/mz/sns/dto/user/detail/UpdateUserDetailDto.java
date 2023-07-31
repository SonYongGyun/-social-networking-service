package kr.co.mz.sns.dto.user.detail;

import lombok.Data;

@Data
public class UpdateUserDetailDto {

  private Long userSeq;
  private String name;
  private String greeting;
}
