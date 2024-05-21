package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;

    public User nicknameUpdate(User user, String nickname) {
        if(!existNickname(nickname)){
            User existingUser = userRepository.findByEmail(user.getEmail()).
                    orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));
            User updatedUser = existingUser.updateNickName(nickname);
            userRepository.save(updatedUser);
            return updatedUser;
        }else{
            throw new IllegalArgumentException("계정명이 중복되었습니다.");
        }
    }

    public User signUp(User user, SignupRequest signupRequest) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));

        User updatedUser = existingUser.updateProfile(signupRequest);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public boolean existNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
