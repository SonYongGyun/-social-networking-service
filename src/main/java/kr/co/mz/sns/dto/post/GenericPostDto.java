package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import kr.co.mz.sns.dto.comment.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericPostDto {

    private Long seq;
    @NotEmpty
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private List<GenericPostFileDto> postFiles;
    private List<CommentDto> comments;

    public GenericPostDto(String content) {
        this.content = content;
    }

    public GenericPostDto(Long seq, String content) {
        this.seq = seq;
        this.content = content;
    }
}
