package WhereWear.server.wherewear.logImage.service;

import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.service.LogService;
import WhereWear.server.wherewear.logImage.repository.LogImageRepository;
import WhereWear.server.wherewear.logImage.domain.LogImage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogImageService {
    private final LogService logService;
    private final LogImageRepository logImageRepository;

    private Storage storage;

    private String BUCKET_NAME = "where-wear-image";
    private String PUBLIC_URL_BASE = "https://storage.googleapis.com/%s/%s";
    private String CLASS_PATH = "wherewear-437004-fdbb62ac26f1.json";

    @PostConstruct
    private void initStorage() throws IOException {
        ClassPathResource resource = new ClassPathResource(CLASS_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public List<String> createImages(List<MultipartFile> imageUrls) throws IOException {
        return imageUrls.stream()
                .map(file -> {
                    try {
                        return uploadFileToGCS(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    public String uploadFileToGCS(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(imageFile.getContentType())
                .build();
        storage.create(blobInfo, imageFile.getInputStream());
        return getPublicUrl(fileName);
    }

    public String getPublicUrl(String fileName) {
        return String.format(PUBLIC_URL_BASE, BUCKET_NAME, fileName);
    }

    public Log deleteImageToLog(Long logId, Long imageId) {
        Log log = logService.findByLogId(logId);
        LogImage logImage = findLogImageById(imageId);

        if (logImage != null) {
            logImage.removeImageFromLog(log);
            logImageRepository.save(logImage);
        }

        return logService.saveLog(log);
    }

    public LogImage findLogImageById(Long imageId){
        return logImageRepository.findLogImageById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected logImage"));
    }

}
