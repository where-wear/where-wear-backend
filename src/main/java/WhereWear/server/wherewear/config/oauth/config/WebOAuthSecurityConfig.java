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

        // CSRF 설정
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키에 저장
                .and()
                .authorizeRequests()
                .requestMatchers("/api/token", "/api/accounts/signUp").permitAll()  // 특정 경로 허용
                .requestMatchers("/api/**").authenticated()  // API 경로는 인증 필요
                .anyRequest().permitAll();  // 나머지 요청은 허용

        // 세션 관리
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT 기반 인증 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // OAuth2 로그인 설정
        http.oauth2Login()
                .loginPage("http://localhost:3000/")  // 로그인 페이지 URL
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())  // 성공 핸들러
                .failureHandler((request, response, exception) -> {
                    System.out.println("OAuth 2.0 로그인 실패: " + exception.getMessage());
                    response.sendRedirect("/login?error=oauth2");  // 실패 시 리디렉션
                })
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);  // 사용자 서비스

        // 로그아웃 설정
        http.logout()
                .logoutUrl("/logout")  // 로그아웃 URL
                .logoutSuccessUrl("/")  // 로그아웃 성공 후 리디렉션할 URL
                .invalidateHttpSession(true)  // 세션 무효화
                .deleteCookies("JSESSIONID")  // 쿠키 삭제
                .permitAll();  // 로그아웃 URL 접근 허용

        // 예외 처리 설정: 인증되지 않은 사용자에 대해 401 UNAUTHORIZED 상태 반환
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));

        return http.build();
    }

    // CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");  // 허용할 프론트엔드 URL
        configuration.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*");  // 모든 헤더 허용
        configuration.setAllowCredentials(true);  // 인증 정보 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
