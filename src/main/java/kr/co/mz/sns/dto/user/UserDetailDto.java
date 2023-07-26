package kr.co.mz.sns.dto.user;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailDto {

  private Long userSeq;
  private String blocked;
  private String greeting;
  private Timestamp createdAt;
  private Timestamp modifiedAt;
  private String profile_picture; //TODO file Table 만들어서 프로필 사진이랑 이랑 post 첨부파일이랑 같이 관리하게만들기.

}
