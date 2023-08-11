package kr.co.mz.sns.entity.user;

import com.github.javafaker.Faker;
import kr.co.mz.sns.repository.user.FriendRelationshipRepository;
import kr.co.mz.sns.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static kr.co.mz.sns.entity.user.constant.FriendRelationshipConst.*;

@Slf4j
@SpringBootTest
public class FriendRelationshipEntityTest {

    @Autowired
    private FriendRelationshipRepository fr_repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Insert Friend-Relationship")
    void addRelationship() {
        var faker = new Faker();

        var randomSeq1 = new Random().nextLong(60, 80);
        var randomSeq2 = new Random().nextLong(60, 80);
// 랜덤하게 두 명의 사용자를 선택합니다.
        var userEntity = userRepository.findBySeq(randomSeq1).orElseThrow();
        var friendEntity = userRepository.findBySeq(randomSeq2).orElseThrow();

        // 동일한 사용자가 선택되지 않도록 합니다.
        while (userEntity.getSeq().equals(friendEntity.getSeq())) {
            friendEntity = userRepository.findBySeq(randomSeq2).orElseThrow();
        }

        var statusList = List.of(
                FR_BLOCKED,
                FR_REJECT,
                FR_WE_ARE_FRIEND,
                FR_WAITING_PERMIT_REQUEST
        );
        var rNum = new Random();

        var fre = FriendRelationshipEntity.builder()
                .status(statusList.get(rNum.nextInt(statusList.size())))
                .userEntity(userEntity)
                .friendEntity(friendEntity)
                .build();

        var savedFre = fr_repository.save(fre);
        log.info(savedFre.toString());

    }

}
