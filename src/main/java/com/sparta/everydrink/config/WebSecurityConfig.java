package com.sparta.everydrink.config;

import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.JwtAuthenticationFilter;
import com.sparta.everydrink.security.JwtAuthorizationFilter;
import com.sparta.everydrink.security.UserDetailsServiceImpl;
import com.sparta.everydrink.security.jwt.JwtService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    public WebSecurityConfig(JwtService jwtService, UserDetailsServiceImpl userDetailsService,
            AuthenticationConfiguration authenticationConfiguration,
            UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtService, userDetailsService);
    }

//    @Bean
//    public JwtRequestFilter jwtRequestFilter() {
//        return new JwtRequestFilter(jwtBlacklistService, jwtService);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/auth/refresh-token").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/post/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/post/{postId}/comment").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 권한
                        // 서버 단에서 에러가 발생시 아래 url이 에러창을 띄워준다
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

//        http.exceptionHandling(auth -> {
//            auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//        });

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(jwtRequestFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}