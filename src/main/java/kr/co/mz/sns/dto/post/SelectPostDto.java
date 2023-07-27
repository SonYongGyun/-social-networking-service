package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.entity.CommentEntity;
import lombok.Data;

@Data
public class SelectPostDto {

    public SelectPostDto(String content) {
        this.content = content;
    }

    private Long seq;
    @NotEmpty
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private List<CommentEntity> comments = new ArrayList<>();
}
