package kr.co.mz.sns.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsedInPostCommentDto {

    private Long seq;
    @NotEmpty
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long postSeq;
    private Long createBy;
    private Long likes;
    private List<CommentFileDto> commentFiles;
    private List<String> mentionedUsername = new ArrayList<>();

    public UsedInPostCommentDto(Long seq, String content, LocalDateTime createdAt, LocalDateTime modifiedAt,
        Long postSeq, Long createBy, Long likes) {
        this.seq = seq;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postSeq = postSeq;
        this.createBy = createBy;
        this.likes = likes;
    }
}
