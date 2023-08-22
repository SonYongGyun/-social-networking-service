package kr.co.mz.sns.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {

  @NotNull
  @NotEmpty
  @Email(message = "Please provide a valid email address")
  private String email;
  
  @NotNull
  @NotEmpty
  private String password;
}
