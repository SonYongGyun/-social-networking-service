package kr.co.mz.sns.service;

import java.util.List;
import kr.co.mz.sns.dto.FindPostDto;
import kr.co.mz.sns.dto.PostSearchDto;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public List<FindPostDto> findByKeyword(PostSearchDto postSearchDto, Pageable pageable) {
        return postRepository.findByContentContaining(postSearchDto.getKeyword(), pageable).stream()
            .map(post -> modelMapper.map(post, FindPostDto.class))
            .toList();
    }

    public List<FindPostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(post -> modelMapper.map(post, FindPostDto.class)).toList();
        // 구현 안된 메서드에 보통 아래의 익셉션을 날림
//        throw new UnsupportedOperationException();
    }

    public FindPostDto findByKey(Long seq) {
        // Declarative Programming or Functional Programming
        return postRepository.findById(seq)
            .map(entity -> modelMapper.map(entity, FindPostDto.class))
            .orElseThrow(() -> new EmptyResultDataAccessException("This post does not exist", 1));

        // Imperative Programming
//        var optionalPost = postRepository.findById(seq);
//        optionalPost.orElseThrow(() -> new EmptyResultDataAccessException("This post does not exist", 1));
//        return modelMapper.map(optionalPost.get(), PostDto.class);
    }

    @Transactional
    public FindPostDto insert(FindPostDto findPostDto) {
        var postEntity = modelMapper.map(findPostDto, PostEntity.class);
        // TODO Generic 한 PostDto 대신 상황에 맞는 DTO를 사용할 것을 권장함
        return modelMapper.map(postRepository.save(postEntity), FindPostDto.class);
    }

    @Transactional
    public FindPostDto updateBySeq(Long seq, FindPostDto findPostDto) {
        // Declarative Programming
        return postRepository.findById(seq)
            .map(entity -> {
                entity.setContent(findPostDto.getContent());
                return entity;
            })
            .map(postRepository::save)
            .map(entity -> modelMapper.map(entity, FindPostDto.class))
            .orElseThrow(() -> new EmptyResultDataAccessException("Post with ID " + seq + "not found", 1));

        // Imperative Programming
//        var optionalPost = postRepository.findById(seq);
//        var postEntity = optionalPost.orElseThrow(
//            () -> new EmptyResultDataAccessException("Post with ID " + seq + "not found", 1));
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var optionalUserEntity = userRepository.findByEmail(userDetails.getUsername());
//        postEntity.setUsers(
//            optionalUserEntity.orElseThrow(() -> new EmptyResultDataAccessException("User information not found", 1))
//        );
//        postEntity.setContent(postDto.getContent());
//        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Transactional
    public void deleteBySeq(Long seq) {
        // Declarative 1
        postRepository.findById(seq)
            .map(entity -> {
                postRepository.delete(entity);
                return entity;
            })
            .orElseThrow(() -> new EmptyResultDataAccessException("Post with ID " + seq + "not found", 1));

        // Declarative 2
//        postRepository.findById(seq)
//            .ifPresentOrElse(
//                postRepository::delete,
//                () -> {
//                    throw new EmptyResultDataAccessException("Post with ID " + seq + "not found", 1);
//                }
//            );
//
//        // Imperative
//        var optionalPostEntity = postRepository.findById(seq);
//        var postEntity = optionalPostEntity.orElseThrow(
//            () -> new EmptyResultDataAccessException("Post with ID " + seq + "not found", 1));
//        postRepository.delete(postEntity);
    }
}