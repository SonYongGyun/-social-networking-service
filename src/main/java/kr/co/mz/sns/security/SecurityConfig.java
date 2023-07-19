package kr.co.mz.sns.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//이건그냥 설정이다.
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/*")
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .httpBasic(Customizer.withDefaults())
    ;
    return http.build();
  }


  @Bean
  public UserDetailsService users() {
    UserDetails admin = User.builder()
        .username("admin")
        .password("1")
        .roles("ADMIN")
        .build();
    UserDetails user = User.builder()
        .username("user")
        .password("password")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(admin, user);
  }
}
