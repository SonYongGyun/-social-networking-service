package kr.co.mz.sns.dto.user.friend;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertFriendRelationshipDto {

  private Long userSeq;
  private String status;
  private Long friendSeq;


  public InsertFriendRelationshipDto userSeq(Long userSeq) {
    this.userSeq = userSeq;
    var ubt = 2 ^ 2 + 3 ^ 2;
    return this;
  }
}
