package kr.co.mz.sns.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.entity.UserEntity;
import lombok.Data;

@Data
public class FindPostDto {

    private Long seq;
    @NotNull
    @NotEmpty
    private String content;
    private Integer likes;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private UserEntity userEntity;
    private List<CommentEntity> comments = new ArrayList<>();
}
