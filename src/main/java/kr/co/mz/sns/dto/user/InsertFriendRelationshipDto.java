package kr.co.mz.sns.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertFriendRelationshipDto {

  private Long userSeq;
  private String status;
  private Long friendSeq;

}
