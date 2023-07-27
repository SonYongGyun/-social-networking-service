package kr.co.mz.sns.dto.user;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class InsertUserDetailDto {

    private Long userSeq;
    private Boolean blocked;
    private String greeting;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Set<GenericUserDetailFileDto> userDetailFileDtoSet;
}
