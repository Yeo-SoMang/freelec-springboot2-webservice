package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
// Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // h2-console화면을 사용하기 위해 해당 옵션들을 disable
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리를 설정하는 옵션의 시작점
                    // 아래를 선언해줘야만 antMatcher를 사용할 수 있음
                    .authorizeRequests()
                    // 권한 대상을 지정하는 옵션
                    // /api/v1/** 주소를 가진 api는 user권한을 가진 사람만 열람가능
                    .antMatchers("/","/css/**", "/images/**",
                            "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 설정된 값들 이외 나머지 URL등을 나타냄
                    // 여기서는 authenticated를 추가해 나머지 url들은 모두 인증된 사용자들에게만 허용
                    .anyRequest().authenticated()
                .and()
                    // 로그아웃 기능에 대한 여러 성정의 진입점
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // oauth2Login 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                        // 로그인 성공 이후 사용자 정보를 가져올 떄의 설정등을 담당
                        .userInfoEndpoint()
                            // 소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체를 등록함
                            .userService(customOAuth2UserService);
    }
}
