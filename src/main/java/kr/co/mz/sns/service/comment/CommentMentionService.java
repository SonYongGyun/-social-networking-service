package kr.co.mz.sns.service.comment;

import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentMentionService {


    public CommentDto split(CommentDto commentDto){
        List<String> userNameList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        String[] usernames = commentDto.getMentionedUsers().split("@");
        for (String username : usernames) {
            if (!username.trim().isEmpty()) {
                userNameList.add(username.trim());
                stringBuilder.append(username.trim());
            }
        }

        commentDto.setMentionedUsers(stringBuilder.toString());

        return commentDto;
    }
}
