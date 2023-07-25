package kr.co.mz.sns.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
}
