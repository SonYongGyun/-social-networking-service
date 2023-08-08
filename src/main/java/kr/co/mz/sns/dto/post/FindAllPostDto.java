package kr.co.mz.sns.dto.post;

import kr.co.mz.sns.dto.post.file.GenericPostFileDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllPostDto {

    public FindAllPostDto(Long seq, String content, Integer likes, LocalDateTime createdAt, LocalDateTime modifiedAt,
                          Long createBy) {
        this.seq = seq;
        this.content = content;
        this.likes = likes;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.createBy = createBy;
    }

    private Long seq;
    private String content;
    private Integer likes = 0;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createBy;
    private GenericPostFileDto postFile;

}
