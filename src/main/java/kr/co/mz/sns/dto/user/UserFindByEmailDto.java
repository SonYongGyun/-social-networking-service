package kr.co.mz.sns.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserFindByEmailDto {

    @NotNull
    @NotEmpty
    private String email;
}
