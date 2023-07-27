package kr.co.mz.sns.service;

import kr.co.mz.sns.dto.user.GenericUserDetailFileDto;
import kr.co.mz.sns.repository.post.PostFileRepository;
import kr.co.mz.sns.repository.user.UserDetailFileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FileService {

  private final UserDetailFileRepository userDetailFileRepository;
  private final PostFileRepository postFileRepository;
  private final ModelMapper modelMapper;

  public GenericUserDetailFileDto findByName(GenericUserDetailFileDto fileDto) {
    return modelMapper.map(userDetailFileRepository.findByName(fileDto.getName()), GenericUserDetailFileDto.class);
  }

}
