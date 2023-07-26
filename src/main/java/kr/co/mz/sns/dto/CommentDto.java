package kr.co.mz.sns.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long seq;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Long postSeq;
    private Long userSeq;
    private boolean like;
}
