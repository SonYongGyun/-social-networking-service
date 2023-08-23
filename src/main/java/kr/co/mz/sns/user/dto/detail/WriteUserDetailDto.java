package kr.co.mz.sns.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WriteUserDetailDto {

    private String name;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
