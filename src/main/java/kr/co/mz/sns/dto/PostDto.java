package kr.co.mz.sns.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class PostDto {
    private int seq;
    private String content;
    private Integer likes;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    private int userSeq;

}
