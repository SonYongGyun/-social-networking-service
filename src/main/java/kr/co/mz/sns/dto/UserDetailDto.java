package kr.co.mz.sns.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class UserDetailDto {

  private Long seq;
  private String blocked;
  private Timestamp createdAt;
  private Timestamp modifiedAt;
  private String profile_picture; //TODO file Table 만들어서 프로필 사진이랑 이랑 post 첨부파일이랑 같이 관리하게만들기.
  private Long user_seq;

}
