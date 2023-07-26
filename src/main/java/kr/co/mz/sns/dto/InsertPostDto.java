package kr.co.mz.sns.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class InsertPostDto {

    private Long seq;
    @NotNull
    @NotEmpty
    private String content;
    private Integer likes;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
