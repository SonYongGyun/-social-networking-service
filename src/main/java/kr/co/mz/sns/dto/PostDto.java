package kr.co.mz.sns.dto;

import java.sql.Timestamp;
import java.util.Date;

public record PostDto(
    int seq,
    String content,
    Timestamp createdAt,
    int likes,
    Timestamp modifiedAt,
    int userSeq
) {

  public PostDto() {
    this(1, "", new Timestamp(new Date().getTime()), 0, new Timestamp(new Date().getTime()), 0);
  }
}