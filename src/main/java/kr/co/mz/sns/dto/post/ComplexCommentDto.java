package kr.co.mz.sns.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexCommentDto {

    private Long commentSeq;
    private String commentContent;
    private Long commentLike;
}
