package kr.co.mz.sns.dto.login;

import lombok.Data;

@Data
public class AuthResponseDto {

  private String accessToken;
  private String tokenType = "Bearer";

  public AuthResponseDto(String accessToken) {
    this.accessToken = accessToken;
  }
}
