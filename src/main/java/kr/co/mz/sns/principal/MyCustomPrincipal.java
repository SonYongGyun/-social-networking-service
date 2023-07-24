package kr.co.mz.sns.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyCustomPrincipal implements UserDetails {

    private String email;
    private String name;
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 Spring Security에서 인식할 수 있는 형식으로 변환하여 반환
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        // 사용자의 비밀번호를 반환 (만약 필요한 경우)
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정이 만료되었는지 여부를 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정이 잠겨있는지 여부를 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자 자격 증명이 만료되었는지 여부를 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정이 활성화되었는지 여부를 반환
        return true;
    }
}
