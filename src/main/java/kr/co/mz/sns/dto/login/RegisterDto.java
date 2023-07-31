package kr.co.mz.sns.dto.login;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {

  @NotNull
  @NotEmpty
  private String email;
  @NotNull
  @NotEmpty
  private String password;
}
