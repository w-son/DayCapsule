package com.son.DayCapsule.config;

import com.son.DayCapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 접근 권한에 대한 설정
    /** 로그인 및 로그아웃 시 SpringSecurity가 UserDetails 객체들에 한에서 세션 관리를 한다 **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/h2-console/**", "/webjars/**").permitAll()
                .antMatchers("/", "/user/signin", "/user/signup").permitAll()
                .antMatchers("/user/main").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/signin") // 이런 요청이 Security의 "/login"을 대체한다
                .defaultSuccessUrl("/user/main") // 로그인 성공시 요청할 주소
                .usernameParameter("username").passwordParameter("password") // 페이지에서 바인딩할 유저 정보 파라미터의 이름, 현재 값은 설정 안할 시의 기본 값이다
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/signout")) // 이 요청이 Security의 "/logout"을 대체한다
                .logoutSuccessUrl("/user/signin") // 로그아웃 성공시 요청할 주소
                .invalidateHttpSession(true);
    }

    /** UserDetailsService 를 Authentication 정보로 등록하는 과정 loadByUsername을 Override 해야한다 **/
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

}
