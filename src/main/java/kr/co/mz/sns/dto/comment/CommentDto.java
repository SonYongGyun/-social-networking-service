package kr.co.mz.sns.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.mz.sns.dto.post.GenericPostFileDto;
import lombok.Data;

@Data
public class CommentDto {

    private Long seq;
    @NotEmpty
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long postSeq;
    private Long userSeq;
    private boolean commentLike;
    private boolean like;
    private List<CommentFileDto> commentFiles;
    private List<String> mentionedUsername = new ArrayList<>();

    public void splitContentAndMentionedUsername() {
        var splitContent = content.split(" ");
        var tempContent = new StringBuilder();

        for(var split : splitContent) {
            if(split.startsWith("@")) {
                mentionedUsername.add(split);
            }
            else {
                tempContent.append(split);
            }
        }

        setContent(tempContent.toString());
    }

}
