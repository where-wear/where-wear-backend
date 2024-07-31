package WhereWear.server.wherewear.config.oauth.service;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환

        // 클라이언트 ID를 통해 구글인지 카카오인지 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 클라이언트 ID에 따라 다른 처리 로직 실행
        if ("google".equals(registrationId)) {
            saveOrUpdateGoogleUser(oAuth2User);
        } else if ("kakao".equals(registrationId)) {
            saveOrUpdateKakaoUser(oAuth2User);
        }

        return oAuth2User;
    }

    private User saveOrUpdateGoogleUser(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Kakao OAuth 사용자 정보에서 필요한 정보 추출
        String email = (String) attributes.get("email");

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.updateEmail(email))
                .orElse(User.builder()
                        .email(email)
                        .build());

        return userRepository.save(user);
    }


    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdateKakaoUser(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Kakao OAuth 사용자 정보에서 필요한 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        String email = (String) kakaoAccount.get("email");

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.updateEmail(email))
                .orElse(User.builder()
                        .email(email)
                        .build());

        return userRepository.save(user);
    }


}
