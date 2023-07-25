package kr.co.mz.sns.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.exception.InsertFailedException;
import kr.co.mz.sns.exception.LoadingFailedException;
import kr.co.mz.sns.repository.PostRepository;
import kr.co.mz.sns.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<PostDto> findAll() {
        try {
            var postEntityList = postRepository.findAll();
            return postEntityList.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        } catch (DataAccessException dae) {
            throw new LoadingFailedException("Unable to load post list : " + dae.getMessage());
        }
    }

    public PostDto findById(Long id) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);
        optionalPost.orElseThrow(() -> new EmptyResultDataAccessException("This post does not exist", 1));
        return modelMapper.map(optionalPost.get(), PostDto.class);
    }

    @Transactional
    public PostDto saveOne(PostDto postDto) {
        var postEntity = modelMapper.map(postDto, PostEntity.class);
        postEntity.setLikes(0);
        postEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var optionalUserEntity = userRepository.findByEmail(userDetails.getUsername());
        postEntity.setUsers(
            optionalUserEntity.orElseThrow(() -> new EmptyResultDataAccessException("User information not found", 1))
        );
        try {
            postRepository.save(postEntity);
        } catch (DataAccessException dae) {
            throw new InsertFailedException("Failed to write post : " + dae.getMessage());
        }
        return postDto;
    }

    @Transactional
    public PostDto updateById(Long id, PostDto postDto) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);
        var postEntity = optionalPost.orElseThrow(
            () -> new EmptyResultDataAccessException("Post with ID " + id + "not found", 1));
        postEntity.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var optionalUserEntity = userRepository.findByEmail(userDetails.getUsername());
        postEntity.setUsers(
            optionalUserEntity.orElseThrow(() -> new EmptyResultDataAccessException("User information not found", 1))
        );
        postEntity.setContent(postDto.getContent());
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    public void deleteById(Long id) {
        try {
            postRepository.deleteById(id);
        } catch (DataAccessException dae) {
            throw new EmptyResultDataAccessException("This post has already been deleted : " + dae.getMessage(), 1);
        }
    }
}