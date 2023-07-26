package kr.co.mz.sns.service;

import java.util.Optional;
import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.exception.CommentNotFoundException;
import kr.co.mz.sns.exception.InsertFailedException;
import kr.co.mz.sns.repository.CommentRepository;
import kr.co.mz.sns.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public CommentDto saveOne(CommentDto commentDto) {
        try {
            CommentEntity commentEntity = commentRepository.save(modelMapper.map(commentDto, CommentEntity.class));
            return modelMapper.map(commentEntity, CommentDto.class);
        } catch (DataAccessException dae) {
            throw new InsertFailedException("Commenting is failed");
        }
    }

    public void deleteOne(Long commentSeq) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));
        commentRepository.delete(commentEntity);
    }

    public void updateOne(CommentDto commentDto) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentDto.getSeq());
        var commentEntity = optional.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));
        commentEntity.setContent(commentDto.getContent());
        commentRepository.save(commentEntity);
    }


}
