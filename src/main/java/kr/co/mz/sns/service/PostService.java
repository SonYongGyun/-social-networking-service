package kr.co.mz.sns.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.entity.PostEntity;
import kr.co.mz.sns.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostService {
    private final PostRepository postRepo;
    private final ModelMapper modelMapper;
    @Autowired
    public PostService(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }
    public List<PostDto> findAll(){
        var postEntityList = postRepo.findAll();
        return postEntityList.stream().map(post -> modelMapper.map(post, PostDto.class))
            .collect(Collectors.toList());
    }
    public PostDto findById(Long id){
        return modelMapper.map(postRepo.findById(id),PostDto.class);
    }
    public PostDto saveOne(PostDto postDto){
        var postEntity = modelMapper.map(postDto,PostEntity.class);
        postRepo.save(postEntity);
        return postDto;
    }
    public void deleteById(Long id) {
        postRepo.deleteById(id);
    }
}
