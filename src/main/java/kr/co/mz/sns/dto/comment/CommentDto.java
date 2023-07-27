package kr.co.mz.sns.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDto {

    private Long seq;
    @NotEmpty
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long postSeq;
    private Long userSeq;
    private String mentionedUsers;
    private boolean commentLike;
    private boolean like;

}
