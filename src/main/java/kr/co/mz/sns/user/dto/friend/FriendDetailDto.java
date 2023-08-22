package kr.co.mz.sns.dto.user.friend;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FriendDetailDto {

    private Long seq;
    private Long userSeq;
    private String status;
    private LocalDateTime createdAt;
    private Long friendSeq;
    private String name;

    public FriendDetailDto(Long seq, Long userSeq, String status, LocalDateTime createdAt, Long friendSeq, String name) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.status = status;
        this.createdAt = createdAt;
        this.friendSeq = friendSeq;
        this.name = name;
    }
}
