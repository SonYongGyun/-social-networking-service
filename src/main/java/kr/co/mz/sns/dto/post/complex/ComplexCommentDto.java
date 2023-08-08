package kr.co.mz.sns.dto.post.complex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexCommentDto {

    private Long commentSeq;
    private String commentContent;
    private Long commentLike;
}
