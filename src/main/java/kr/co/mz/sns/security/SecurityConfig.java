package kr.co.mz.sns.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JWTService jwtService;
  private final CustomUserDetailService customUserDetailService;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;

  public SecurityConfig(JWTService jwtService, CustomUserDetailService customUserDetailService,
      JwtAuthEntryPoint jwtAuthEntryPoint) {
    this.jwtService = jwtService;
    this.customUserDetailService = customUserDetailService;
    this.jwtAuthEntryPoint = jwtAuthEntryPoint;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
//                .requestMatchers("/login/*")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
        ).exceptionHandling(
            (exceptionHandlingConfigurer) -> exceptionHandlingConfigurer.authenticationEntryPoint(
                jwtAuthEntryPoint))

    ;
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

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtService, customUserDetailService);
  }

}
