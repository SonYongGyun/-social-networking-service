package kr.co.mz.sns.repository.post;

import java.util.ArrayList;
import kr.co.mz.sns.dto.post.ComplexPostDto;
import kr.co.mz.sns.repository.comment.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostFileRepository postFileRepository;
    @Autowired
    private CommentRepository commentRepository;


    //    @Test
    void testFetchJoin() {
        var postWithFiles = postRepository.fetchAll();

        var complexPosts = ComplexPostDto.of(postWithFiles);

        System.out.println(complexPosts.stream().map(ComplexPostDto::getPostSeq).toList());
    }

    //    @Test
    void testFetchTest() {
        var postDtoList = postRepository.fetchPostAll();

        postDtoList.forEach(entity -> {
            System.out.print(entity.getSeq());
            System.out.println(entity.getPostFiles());
        });
    }

    @Test
    @Transactional
    void testFindAll() {
        var postSeqs = new ArrayList<Long>();
        var pageable = PageRequest.of(0, 10);

        log.info("------------------------Post-------------------");
        postRepository.findAllWithPaging(pageable).forEach(
            (entity) -> {
                log.info("postSeq : " + entity.getSeq().toString());
                log.info("content : " + entity.getContent());
                postSeqs.add(entity.getSeq());
            }
        );

        log.info("---------------------PostFile------------------");
        postFileRepository.findAllByPostSeqs(postSeqs).forEach(
            (entity -> {
                log.info("postSeq : " + entity.getPostSeq().toString());
                log.info("fileName : " + entity.getName());
            })
        );

        log.info("----------------------Comment------------------");
        commentRepository.findAllByPostSeqs(postSeqs).forEach(
            (entity) -> {
                log.info("postSeq : " + entity.getPostSeq().toString());
                log.info("content : " + entity.getContent());
            }
        );

    }
//    @Test
//    @Transactional
//    void testCombine

}
