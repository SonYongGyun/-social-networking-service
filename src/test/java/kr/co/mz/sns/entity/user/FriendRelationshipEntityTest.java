package kr.co.mz.sns.entity.user;

import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.FR_BLOCKED;

@SpringBootTest
public class FriendRelationshipEntityTest {

    @Autowired
    private FriendRelationshipRepository FR_repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Insert Friend-Relationship")
    void addRelationship() {
        var user1 = userRepository.findBySeq(79L);
        var user2 = userRepository.findBySeq(80L);

        var fre = FriendRelationshipEntity.builder()
                .status(FR_BLOCKED)
                .build();

//        FR_repository.save();
    }

}
