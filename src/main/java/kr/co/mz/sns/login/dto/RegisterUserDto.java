package kr.co.mz.sns.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto {

  @NotNull
  @NotEmpty
  @Email
  private String email;
  @NotNull
  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  private String password;
}
