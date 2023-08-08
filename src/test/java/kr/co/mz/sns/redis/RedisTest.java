package kr.co.mz.sns.redis;

import kr.co.mz.sns.repository.post.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RedisService redisService;

    @Test
    void testSave() {
        var postEntity = postRepository.findBySeqWithPostFilesAndComments(74L).get();
        System.out.println(postEntity);
        redisService.saveToCache("PostEntity:" + postEntity.getSeq(), postEntity);
    }

    //    @Test
    void testGet() {
        var postEntity = redisService.getFromCache("PostEntity:" + 74);
        System.out.println(postEntity);
    }

    //    @Test
    void testDelete() {
        redisService.deleteFromCache("PostEntity:" + 74);
    }


}
