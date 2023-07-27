package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import kr.co.mz.sns.entity.comment.CommentEntity;
import lombok.Data;

@Data
public class SelectPostDto {

    private Long seq;
    @NotEmpty
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private Set<GenericPostFileDto> postFileDtoSet = new HashSet<>();
    private Set<CommentEntity> comments = new HashSet<>();

    public SelectPostDto(String content) {
        this.content = content;
    }
}
