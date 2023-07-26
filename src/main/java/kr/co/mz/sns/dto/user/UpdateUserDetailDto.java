package kr.co.mz.sns.dto.user;

import lombok.Data;

@Data
public class UpdateUserDetailDto {

    private Long userSeq;
    private String greeting;
    private String profile_picture;
}
