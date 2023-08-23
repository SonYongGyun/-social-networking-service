package kr.co.mz.sns.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto1 {

    private Long seq;
    private String email;
    private String password;
    private String role;
    private String name;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
