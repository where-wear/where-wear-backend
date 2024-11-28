package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.account.dto.UpdateRequest;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;

    public String existNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("계정명이 중복되었습니다.");
        } else {
            return nickname;
        }
    }

    public User updateUserInfo(User user, UpdateRequest updateRequest) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getNickname()));

        User updatedUser = existingUser.updateProfile(updateRequest);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public User getMyInfo(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        return existingUser;
    }
}
