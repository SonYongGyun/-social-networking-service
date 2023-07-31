package kr.co.mz.sns.dto.user.detail;

import lombok.Data;

@Data
public class InsertUserDetailDto {

  private Boolean blocked;
  private String name;
  private String greeting;
}
