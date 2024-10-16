package WhereWear.server.wherewear.log.logImage;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
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
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class LogImageService {
    private final LogService logService;
    private final LogImageRepository logImageRepository;
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String BUCKET_NAME = "where-wear-image"; // GCS 버킷 이름

    public Log addImageToLog(Long logId, MultipartFile imageFile) throws IOException {
        ClassPathResource resource = new ClassPathResource("wherewear-437004-fdbb62ac26f1.json");

        // GoogleCredentials를 사용하여 인증 정보 로드
        InputStream credentialsStream = resource.getInputStream();
        // GoogleCredentials를 사용하여 인증 정보 로드
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        // 인증 정보를 포함하여 Storage 객체 생성
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

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
        String publicUrl =  getPublicUrl(fileName);

        Log log = logService.findByLogId(logId);

        LogImage logImage = createImage(publicUrl);

        logImage.setLog(log);

        return logService.saveLog(log);
    }

    public String getPublicUrl(String fileName) {
        return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, fileName);
    }

    public Log deleteImageToLog(Long logId, Long imageId) {
        Log log = logService.findByLogId(logId);
        LogImage logImage = findLogImageById(imageId);

        if (logImage != null) {
            logImage.removeImageFromLog(log);
            save(logImage);
        }

        return logService.saveLog(log);
    }

    public LogImage createImage(String publicUrl){
        LogImage logImage = new LogImage(publicUrl);
        return save(logImage);
    }

    public LogImage save(LogImage logImage){
        return logImageRepository.save(logImage);
    }

    public LogImage findLogImageById(Long imageId){
        return logImageRepository.findLogImageById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected logImage"));
    }

}
