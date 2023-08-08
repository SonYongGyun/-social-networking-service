package kr.co.mz.sns.dto.post.test;

import kr.co.mz.sns.dto.comment.UsedInPostCommentDto;
import kr.co.mz.sns.dto.post.file.GenericPostFileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostTestDto {

    public PostTestDto(Long seq, String content, Integer likes, LocalDateTime createdAt, LocalDateTime modifiedAt,
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
    private List<GenericPostFileDto> postFiles = new ArrayList<>();
    private List<UsedInPostCommentDto> comments = new ArrayList<>();

    public void setPostFiles(GenericPostFileDto postFile) {
        postFiles.add(postFile);
    }

    public void setComments(UsedInPostCommentDto comment) {
        comments.add(comment);
    }
}
