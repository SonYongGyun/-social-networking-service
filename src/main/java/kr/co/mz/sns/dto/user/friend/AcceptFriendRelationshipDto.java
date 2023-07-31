package kr.co.mz.sns.dto.user.friend;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_WE_ARE_FRIEND;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptFriendRelationshipDto {

  private Long userSeq;
  private String status = FR_WE_ARE_FRIEND;
  private Long friendSeq;
}
