package kr.co.mz.sns.config.security;

import java.util.List;
import kr.co.mz.sns.dto.user.GenericUserDto;
import kr.co.mz.sns.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new CustomUserDetails(modelMapper.map(userEntity, GenericUserDto.class),
            List.of(new SimpleGrantedAuthority(userEntity.getRole())));
    }
}
