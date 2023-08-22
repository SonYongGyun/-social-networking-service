package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GenericUserDto {

    private Long seq;
    private String email;
    private String name;
    private String password;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String role;
}
