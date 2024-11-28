package WhereWear.server.wherewear.log.service;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.logFashion.LogFashionService;
import WhereWear.server.wherewear.logImage.LogImageService;
import WhereWear.server.wherewear.logPlace.LogPlaceService;
import WhereWear.server.wherewear.logTag.LogTagService;
import WhereWear.server.wherewear.logText.LogTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateLogService {
    private final LogService logService;
    private final LogFashionService logFashionService;
    private final LogPlaceService logPlaceService;
    private final LogTextService logTextService;
    private final LogImageService logImageService;
    private final LogTagService logTagService;

    public Log create(String email,
                      String text,
                      List<MultipartFile> imageUrls,
                      List<FashionItemRequest> items,
                      double x,
                      double y,
                      String address,
                      String placeName,
                      Boolean isShow,
                      List<String> tags) throws IOException {

        Log log = logService.startLog(email);

        for (FashionItemRequest item : items) {
            logFashionService.addFashionItemToLog(log.getId(), item.getCategoryId(), item.getItemName());
        }

        logPlaceService.addPlaceToLog(log.getId(), x, y, address, placeName);

        logTextService.addTextToLog(log.getId(), text);

        for (MultipartFile file : imageUrls) {
            logImageService.addImageToLog(log.getId(), file);
        }

        for (String tag : tags) {
            logTagService.addTagToLog(log.getId(), tag);
        }

        setIsShow(log, isShow);

        return log;
    }

    private void setIsShow(Log log, Boolean isShow) {
        log.setIsShow(isShow);
        logService.saveLog(log);
    }
}
