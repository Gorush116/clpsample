package com.clp.stexample.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests()
            .requestMatchers("/**").permitAll()  // 특정 경로에 대한 접근 허용
            .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
/*            .and()
        .formLogin()
            .loginPage("/login")  // 사용자 정의 로그인 페이지
            .permitAll()  // 로그인 페이지 접근 허용
            .and()
        .logout()
            .permitAll() // 로그아웃 허용
            */
           ;  

        return http.build();  // SecurityFilterChain 빌드
    }
}
