package kr.co.mz.sns.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JWTService jwtService;
  private final CustomUserDetailService customUserDetailService;

  @Autowired
  public SecurityConfig(JWTService jwtService, CustomUserDetailService customUserDetailService) {
    this.jwtService = jwtService;
    this.customUserDetailService = customUserDetailService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(new JwtAuthenticationFilter(jwtService, customUserDetailService),
            UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers("/api/unauth/*").permitAll()
                .requestMatchers("/api/public/*").hasAnyRole("ANONYMOUS", "MEMBER")
                .requestMatchers("/api/auth/*").hasRole("MEMBER")
                .anyRequest().authenticated()
        );
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
