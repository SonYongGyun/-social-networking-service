package kr.co.mz.sns.repository.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.mz.sns.dto.post.complex.ComplexPostDto;
import kr.co.mz.sns.dto.post.test.PostTestDto;
import kr.co.mz.sns.repository.comment.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@SpringBootTest
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
//            System.out.println(entity.getPostFiles());
        });
    }

    //    @Test
    void testConverter() {
        var postEntity = postRepository.findById(74L).get();
        System.out.println(postEntity.getPostVisibility());
    }

    @Transactional
//    @Test
    void testFindAllUsedMap() {
        var postSeqs = new ArrayList<Long>();
        var pageable = PageRequest.of(0, 10);

        log.info("--------------------------------------Post---------------------------------");
        var postDtoMap = new HashMap<Long, PostTestDto>();
        postRepository.findAllWithPagingForTest(pageable).forEach(
                (postDto) -> {
                    log.info("postSeq : " + postDto.getSeq().toString());
                    log.info("content : " + postDto.getContent());
                    postSeqs.add(postDto.getSeq());
                    postDtoMap.put(postDto.getSeq(), postDto);
                }
        );

        log.info("-----------------------------------PostFile--------------------------------");
        postFileRepository.findAllByPostSeqsForTest(postSeqs).forEach(
                (fileDto -> {
                    log.info("postSeq : " + fileDto.getPostSeq().toString());
                    log.info("fileName : " + fileDto.getName());
                    if (postDtoMap.containsKey(fileDto.getPostSeq())) {
                        var postDto = postDtoMap.get(fileDto.getPostSeq());
                        postDto.setPostFiles(fileDto);
                    }
                })
        );

        log.info("------------------------------------Comment--------------------------------");
        commentRepository.findAllByPostSeqs(postSeqs).forEach(
                commentDto -> {
                    log.info("postSeq : " + commentDto.getPostSeq().toString());
                    log.info("content : " + commentDto.getContent());
                    if (postDtoMap.containsKey(commentDto.getPostSeq())) {
                        var postDto = postDtoMap.get(commentDto.getPostSeq());
                        postDto.setComments(commentDto);
                    }
                }
        );

        log.info("----------------------------------CombinePostDto------------------------------");
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        postDtoMap.forEach((key, value) -> {
            try {
                System.out.println(objectMapper.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

