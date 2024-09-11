package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.account.dto.SignupRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.account.dto.UpdateRequest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String BUCKET_NAME = "wherewear-image"; // GCS 버킷 이름
    public String existNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()){
            throw new IllegalArgumentException("계정명이 중복되었습니다.");
        }else{
            return nickname;
        }
    }

    public User signUp(User user, SignupRequest signupRequest) throws IOException {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));

        setProfileImage(existingUser, signupRequest.getImage());
        User updatedUser = existingUser.signUp(signupRequest);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public void setProfileImage(User user, MultipartFile file) throws IOException {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));

        String fileName = file.getOriginalFilename();
        // BlobId: 버킷 이름과 파일 이름으로 구성된 고유 식별자
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);

        // BlobInfo: 파일의 메타데이터 (버킷과 파일 이름, MIME 타입 등)
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType()) // 파일의 MIME 타입
                .build();

        // 파일 업로드
        storage.create(blobInfo, file.getInputStream());
        // 업로드된 파일의 공개 URL 생성
        String publicUrl =  getPublicUrl(fileName);
        User updatedUser = existingUser.updateProfileImage(publicUrl);
        userRepository.save(updatedUser);
    }

    public String getPublicUrl(String fileName) {
        // 15분 동안 유효한 서명된 URL 생성
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        URL url = storage.signUrl(BlobInfo.newBuilder(blobId).build(), 15, TimeUnit.MINUTES);
        return url.toString();
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

    public void withdraw(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        userRepository.delete(existingUser);
    }
}