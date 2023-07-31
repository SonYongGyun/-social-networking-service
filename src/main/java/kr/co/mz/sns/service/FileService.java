package kr.co.mz.sns.service;

import kr.co.mz.sns.dto.user.detail.CompleteUserProfileDto;
import kr.co.mz.sns.repository.post.PostFileRepository;
import kr.co.mz.sns.repository.user.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FileService {

  private final UserProfileRepository userProfileRepository;
  private final PostFileRepository postFileRepository;
  private final ModelMapper modelMapper;

  public CompleteUserProfileDto findByName(CompleteUserProfileDto fileDto) {
    return modelMapper.map(userProfileRepository.findByName(fileDto.getName()), CompleteUserProfileDto.class);
  }

}
