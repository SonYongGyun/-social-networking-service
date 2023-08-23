package kr.co.mz.sns.user.dto.detail.projection;

import java.time.LocalDateTime;

public interface UserDetailAndProfileProjection {
    Long getDetailSeq();

    Long userSeq();

    boolean blocked();

    String getGreeting();

    LocalDateTime getDetailCreatedAt();

    LocalDateTime getDetailModifiedAt();

    Long getProfileSeq();

    String getUuid();

    String getName();

    String getPath();

    Long getSize();

    String getExtension();

    LocalDateTime getFileCreatedAt();

    LocalDateTime getFileModifiedAt();
}
