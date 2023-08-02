package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import kr.co.mz.sns.dto.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenericPostDto {

    private Long seq;
    @NotEmpty
    @NotNull
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private Set<CommentDto> comments;
    private Set<GenericPostFileDto> postFiles;
}
