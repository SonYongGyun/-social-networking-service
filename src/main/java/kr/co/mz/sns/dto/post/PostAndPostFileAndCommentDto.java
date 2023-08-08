package kr.co.mz.sns.dto.post;

import java.time.LocalDateTime;

public interface PostAndPostFileAndCommentDto {

    Long getPostSeq();

    String getPostContent();

    Integer getPostLikes();

    Long getPostCreateBy();

    LocalDateTime getPostCreatedAt();

    LocalDateTime getPostModifiedAt();

    Long getFileSeq();

    String getFileName();

    String getFilePath();

    Long getFileSize();

    String getFileExtension();

    Long getCommentSeq();

    String getCommentContent();

    Long getCommentLike();
}
