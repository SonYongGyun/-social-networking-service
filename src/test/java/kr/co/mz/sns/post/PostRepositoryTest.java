package kr.co.mz.sns.post;

import static org.junit.jupiter.api.Assertions.assertFalse;

import kr.co.mz.sns.repository.post.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  void test() {
    var postEntityList = postRepository.fetchAll();

    assertFalse(postEntityList.isEmpty());

    log.info("-----------------comments-----------------");
    postEntityList.forEach(postEntity -> {
      System.out.println(postEntity.getComments());
    });
    log.info("-----------------postFiles-----------------");
    postEntityList.forEach(postEntity -> {
      System.out.println(postEntity.getPostFiles());
    });
  }
}
