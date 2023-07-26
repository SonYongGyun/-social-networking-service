package kr.co.mz.sns.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFindByEmailDto {

  @NotNull
  @NotEmpty
  private String email;
}
