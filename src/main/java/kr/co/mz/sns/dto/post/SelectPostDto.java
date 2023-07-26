package kr.co.mz.sns.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.entity.CommentEntity;
import lombok.Data;

@Data
public class SelectPostDto {

    private Long seq;
    @NotNull
    @NotEmpty
    private String content;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userSeq;
    private List<CommentEntity> comments = new ArrayList<>();
}
