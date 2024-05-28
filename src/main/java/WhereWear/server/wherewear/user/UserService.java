package WhereWear.server.wherewear.user;

import WhereWear.server.wherewear.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public User findByAccessToken(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!tokenProvider.validToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Authentication authentication = tokenProvider.getAuthentication(token);
        return (User) authentication.getPrincipal();

    }
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}