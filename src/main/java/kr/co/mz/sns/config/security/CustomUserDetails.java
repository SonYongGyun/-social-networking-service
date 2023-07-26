package kr.co.mz.sns.config.security;

import java.util.Collection;
import kr.co.mz.sns.dto.user.GenericUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

    private final GenericUserDto genericUserDto;

    public CustomUserDetails(GenericUserDto genericUserDto, Collection<? extends GrantedAuthority> authorities) {
        super(genericUserDto.getEmail(), genericUserDto.getPassword(), authorities);
        this.genericUserDto = genericUserDto;
    }

    public GenericUserDto getUserDto() {
        System.out.println("userDto : " + genericUserDto);
        return genericUserDto;
    }
}
