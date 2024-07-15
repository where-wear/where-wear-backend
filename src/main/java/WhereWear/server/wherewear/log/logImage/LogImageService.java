package WhereWear.server.wherewear.log.logImage;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogImageService {
    private final LogService logService;
    private final LogImageRepository logImageRepository;

    public Log addImageToLog(Long logId, String imageData, String imageName) {
        Log log = logService.findByLogId(logId);
        LogImage logImage = createImage(imageData, imageName);

        logImage.setLog(log);

        return logService.saveLog(log);
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

    public LogImage createImage(String imageData, String imageName){
        LogImage logImage = new LogImage(imageData, imageName);
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
