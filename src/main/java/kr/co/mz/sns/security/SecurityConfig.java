package kr.co.mz.sns.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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

  private JwtAuthEntryPoint authEntryPoint;
  private CustomUserDetailService userDetailService;


  @Autowired
  public SecurityConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailService userDetailService) {
    this.authEntryPoint = authEntryPoint;
    this.userDetailService = userDetailService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//이건그냥 설정이다.
    http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling((exceptionhandling) -> exceptionhandling.accessDeniedPage("/error/401"))
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

//  @Bean
//  public UserDetailsService users() {
//    UserDetails admin = User.builder()
//        .username("admin")
//        .password("1")
//        .roles("ADMIN")
//        .build();
//    UserDetails user = User.builder()
//        .username("user")
//        .password("2")
//        .roles("USER")
//        .build();
//    return new InMemoryUserDetailsManager(admin, user);
//  }

  @Bean
  public AuthenticationManager authenticationManager(//일단 개발용이니까 뭐 아무런권한없이 만들어준다.
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

}
