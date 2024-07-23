package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.account.dto.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;

    public String existNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()){
            throw new IllegalArgumentException("계정명이 중복되었습니다.");
        }else{
            return nickname;
        }
    }

    public User signUp(User user, SignupRequest signupRequest) {
        User existingUser = userRepository.findByNickname(user.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getNickname()));

        User updatedUser = existingUser.signUp(signupRequest);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public User updateUserInfo(User user, UpdateRequest updateRequest) {
        User existingUser = userRepository.findByNickname(user.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getNickname()));

        User updatedUser = existingUser.updateProfile(updateRequest);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public User getMyInfo(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getNickname()));

        return existingUser;
    }
}