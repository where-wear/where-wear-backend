package WhereWear.server.wherewear.config.oauth.config;

import WhereWear.server.wherewear.config.jwt.TokenProvider;
import WhereWear.server.wherewear.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import WhereWear.server.wherewear.config.oauth.OAuth2SuccessHandler;
import WhereWear.server.wherewear.config.oauth.service.OAuth2UserCustomService;
import WhereWear.server.wherewear.config.oauth.filter.TokenAuthenticationFilter;
import WhereWear.server.wherewear.refreshToken.RefreshTokenRepository;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    /*@Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console());
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.cors();

        http.authorizeRequests()
                .requestMatchers("/api/token","/api/accounts/signUp","/api/place/**","/api/explore/**","/api/log/getLogs/**","/api/explore").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http.oauth2Login()
                .loginPage("http://localhost:3000/")
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())
                .failureHandler((request, response, exception) -> {
                    // 실패 원인을 로그에 출력하거나 다른 작업을 수행할 수 있습니다.
                    System.out.println("OAuth 2.0 로그인 실패: " + exception.getMessage());
                    // 실패 후에 사용자를 리디렉트하거나 다른 동작을 수행할 수 있습니다.
                    response.sendRedirect("/login?error=oauth2");
                })
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutUrl("/logout")  // 로그아웃 URL 설정
                .logoutSuccessUrl("/")  // 로그아웃 성공 후 리디렉션할 URL 설정
                .invalidateHttpSession(true)  // 세션 무효화
                .deleteCookies("JSESSIONID")  // 쿠키 삭제
                .permitAll();  // 로그아웃 URL은 모두 접근 가능하게 설정

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));

        return http.build();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                userRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

}