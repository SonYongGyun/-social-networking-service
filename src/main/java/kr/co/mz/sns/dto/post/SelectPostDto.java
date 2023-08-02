package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import kr.co.mz.sns.dto.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SelectPostDto {

    private Long seq;
    @NotEmpty
    @NotNull
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private List<GenericPostFileDto> postFiles;
    private List<CommentDto> comments;

    public SelectPostDto(Long seq) {
        this.seq = seq;
    }
}
