package kr.co.mz.sns.dto.user.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestedRelationshipDto {

  private String status;
  private Long requesterSeq;
}
