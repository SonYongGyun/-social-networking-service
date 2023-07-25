package kr.co.mz.sns.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class UserDto {

    private Long seq;
    private String email;
    private String name;
    private String password;
    private Timestamp lastLoginAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private String role;

}
