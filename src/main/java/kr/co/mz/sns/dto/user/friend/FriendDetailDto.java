package kr.co.mz.sns.dto.user.friend;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendDetailDto {

  private Long seq;
  private Long userSeq;
  private String status;
  private LocalDateTime createdAt;
  private Long friendSeq;
  private String name;
  private String greeting;
  private LocalDateTime lastLoginAt;

  public FriendDetailDto(Long seq, Long userSeq, String status, LocalDateTime createdAt, Long friendSeq, String name,
      String greeting, LocalDateTime lastLoginAt) {
    this.seq = seq;
    this.userSeq = userSeq;
    this.status = status;
    this.createdAt = createdAt;
    this.friendSeq = friendSeq;
    this.name = name;
    this.greeting = greeting;
    this.lastLoginAt = lastLoginAt;
  }

  public FriendDetailDto(Long seq, Long userSeq, String status, LocalDateTime createdAt, Long friendSeq, String name) {
    this.seq = seq;
    this.userSeq = userSeq;
    this.status = status;
    this.createdAt = createdAt;
    this.friendSeq = friendSeq;
    this.name = name;
  }
}
