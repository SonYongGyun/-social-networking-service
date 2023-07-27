package kr.co.mz.sns.service.user;

import kr.co.mz.sns.dto.user.GenericUserDetailFileDto;
import kr.co.mz.sns.repository.user.UserDetailFileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDetailFileService {

  private final UserDetailFileRepository userDetailFileRepository;
  private final ModelMapper modelMapper;

  public GenericUserDetailFileDto findByName(GenericUserDetailFileDto fileDto) {
    return modelMapper.map(userDetailFileRepository.findByName(fileDto.getName()), GenericUserDetailFileDto.class);
  }

}
