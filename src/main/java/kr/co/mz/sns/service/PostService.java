package kr.co.mz.sns.service;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.exception.InsertFailedException;
import kr.co.mz.sns.exception.PostNotFoundException;
import kr.co.mz.sns.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    public List<PostDto> findAll(){
        try{
            var postEntityList = postRepository.findAll();
            return postEntityList.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        } catch (DataAccessException dae){
            throw new PostNotFoundException("Unable to load post list");
        }
    }
    public PostDto findById(Long id){
        Optional<PostEntity> optionalPost = postRepository.findById(id);
        optionalPost.orElseThrow(()->new PostNotFoundException("This post does not exist"));
        return modelMapper.map(optionalPost.get(), PostDto.class);
    }
    public PostDto saveOne(PostDto postDto){
        var postEntity = modelMapper.map(postDto,PostEntity.class);
        try{
            postRepository.save(postEntity);
        }
        catch(DataAccessException de){
            throw new InsertFailedException("Failed to write post");
        }
        return postDto;
    }
    public PostDto updateById(Long id, PostDto postDto) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);
        var postEntity = optionalPost.orElseThrow(() -> new PostNotFoundException("Post with ID " + id + "not found"));
        if (postDto.getContent() != null) {
            postEntity.setContent(postDto.getContent());
        }
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }
    public void deleteById(Long id) {
        try {
            postRepository.deleteById(id);
        }catch (EmptyResultDataAccessException erdae){
            throw new PostNotFoundException("This post has already been deleted");
        }
    }
}
