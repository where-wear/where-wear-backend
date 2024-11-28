package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
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
public class SignUpService {
    private final UserRepository userRepository;
    private final String BUCKET_NAME = "where-wear-image"; // GCS 버킷 이름
    private final String PUBLIC_URL_FORMAT = "https://storage.googleapis.com/%s/%s";
    private final String CLASS_PATH_RESOURCE = "wherewear-437004-fdbb62ac26f1.json";

    public User signUp(User user, String nickname, int height, int weight, int footSize, String job, String introduction, MultipartFile imageFile) throws IOException {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));

        setProfileImage(existingUser, imageFile);
        User updatedUser = existingUser.signUp(nickname, height, weight, footSize, job, introduction);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    private void setProfileImage(User user, MultipartFile imageFile) throws IOException {
        ClassPathResource resource = new ClassPathResource(CLASS_PATH_RESOURCE);

        // GoogleCredentials를 사용하여 인증 정보 로드
        InputStream credentialsStream = resource.getInputStream();
        // GoogleCredentials를 사용하여 인증 정보 로드
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        // 인증 정보를 포함하여 Storage 객체 생성
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getEmail()));

        String fileName = imageFile.getOriginalFilename();

        // BlobId: 버킷 이름과 파일 이름으로 구성된 고유 식별자
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);

        // BlobInfo: 파일의 메타데이터 (버킷과 파일 이름, MIME 타입 등)
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(imageFile.getContentType()) // 파일의 MIME 타입
                .build();

        // 파일 업로드
        storage.create(blobInfo, imageFile.getInputStream());
        // 업로드된 파일의 공개 URL 생성
        String publicUrl = getPublicUrl(fileName);
        User updatedUser = existingUser.updateProfileImage(publicUrl);
        userRepository.save(updatedUser);
    }

    private String getPublicUrl(String fileName) {
        return String.format(PUBLIC_URL_FORMAT, BUCKET_NAME, fileName);
    }
}
