package kr.co.mz.sns.comment;

import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.repository.CommentRepository;
import kr.co.mz.sns.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentTest {

    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, new ModelMapper());
    }
    @Test
    @DisplayName("댓글저장 테스트")
    void save(){
        CommentDto commentDto = new CommentDto(4L, "content", null, null, 2L, 4L);
        commentService.saveOne(commentDto);
    }
    @Test
    @DisplayName("모든글 검색")
    void findAll(){
        CommentDto commentDto = new CommentDto();
        List<CommentDto> list = commentService.viewAll(2L);
        for(CommentDto commentDto1 : list){
            System.out.println(commentDto1.getSeq());
        }
    }

    @Test
    @DisplayName("댓글 수정")
    void update(){
        CommentDto commentDto = new CommentDto(4L, "updateContent", null, null, 2L, 4L);
        commentService.updateOne(4L, commentDto);

    }

    @Test
    @DisplayName("댓글 삭제")
    void delete(){
        commentService.deleteOne(1L);
    }
}
