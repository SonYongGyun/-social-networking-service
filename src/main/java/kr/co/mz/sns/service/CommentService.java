package kr.co.mz.sns.service;

import kr.co.mz.sns.dto.CommentDto;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.entity.CommentEntity;
import kr.co.mz.sns.exception.CommentNotFoundException;
import kr.co.mz.sns.exception.InsertFailedException;
import kr.co.mz.sns.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public List<CommentDto> viewAll(Long postSeq){
        Optional<List<CommentEntity>> optional = commentRepository.findByPostSeq(postSeq);
        var commentEntityList = optional.orElseThrow(() -> new CommentNotFoundException("It is not existed comment."));
        return commentEntityList.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList();
    }

    public CommentDto saveOne(CommentDto commentDto){
        try {
            CommentEntity commentEntity = commentRepository.save(modelMapper.map(commentDto, CommentEntity.class));
            return modelMapper.map(commentEntity, CommentDto.class);
        }catch(DataAccessException dae){
            throw new InsertFailedException("Commenting is failed");
        }
    }

    public void deleteOne(Long commentSeq) {
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));
        commentRepository.delete(commentEntity);
    }

    public void updateOne(Long commentSeq, CommentDto commentDto){
        Optional<CommentEntity> optional = commentRepository.findBySeq(commentSeq);
        var commentEntity = optional.orElseThrow(() -> new CommentNotFoundException("It is not exist comment"));
        var updateCommentEntity = commentRepository.save(modelMapper.map(commentDto, CommentEntity.class));
        commentRepository.save(updateCommentEntity);
    }



}
