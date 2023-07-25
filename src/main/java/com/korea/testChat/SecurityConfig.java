//로그인관련 나중에 추가할 부분
package com.korea.testChat;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //구글api추가사항 시작
//                .requestMatchers("/security-login/info").authenticated()
//                .requestMatchers("/security-login/admin/**").hasAuthority(UserRole.ADMIN.name()).anyRequest().permitAll()
                //구글api추가사항 끝
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                .and()
                //구글api추가사항 시작
//                .formLogin((formLogin)->formLogin
//                        .usernameParameter("lo oauth2ginId")
//                        .passwordParameter("password")
//                        .loginPage("/security-login/login")
//                        .defaultSuccessUrl("/security-login")
//                        .failureUrl("/security-login/login"))
////and
//                .logout((logout)->logout
//                        .logoutUrl("/security-login/logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID"))
//and           //구글api추가사항 끝
                .csrf((csrf)->csrf.disable())
//and
                .headers(headers->headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
//and
                .formLogin((formLogin)->formLogin
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/"))
//and
                .logout((logout)->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))

        ;


        return http.build();
    }

    @Bean//를 Spring에 등록해놓고 비밀번호 암호화, 비밀번호 체크할 때 사용용도
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //    로그인시 필요한 Bean 코드
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}