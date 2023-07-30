package kr.co.mz.sns.service.comment;

import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentMentionService {


    public Optional<List<String>> split(CommentDto commentDto){
        List<String> userNameList = new ArrayList<>();
        var stringBuilder = new StringBuilder();
        var mentionedUsers = commentDto.getMentionedUsers();
        if (mentionedUsers == null) {
            return Optional.empty();
        }
        String[] usernames = mentionedUsers.split("@");
        for (String username : usernames) {
            if (!username.trim().isEmpty()) {
                userNameList.add(username.trim());
                stringBuilder.append(username.trim());
            }
        }
        commentDto.setMentionedUsers(stringBuilder.toString());

        return Optional.of(userNameList);
    }
}
