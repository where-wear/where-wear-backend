package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import WhereWear.server.wherewear.log.fashion.LogFashionService;
import WhereWear.server.wherewear.log.logImage.LogImageRequest;
import WhereWear.server.wherewear.log.logImage.LogImageService;
import WhereWear.server.wherewear.log.place.LogPlaceService;
import WhereWear.server.wherewear.log.tag.LogTagService;
import WhereWear.server.wherewear.log.text.LogTextService;
import WhereWear.server.wherewear.log.user.LogUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateLogService {
    private final LogService logService;
    private final LogUserService logUserService;
    private final LogFashionService logFashionService;
    private final LogPlaceService logPlaceService;
    private final LogTextService logTextService;
    private final LogImageService logImageService;
    private final LogTagService logTagService;
    public Log create(String nickName, LogRequest request) {

        Log log = logService.startLog();

        logUserService.addUserToLog(log.getId(), nickName);

        for (FashionItemRequest item : request.getItems()){
            logFashionService.addFashionItemToLog(log.getId(), item.getCategoryId(), item.getItemName());
        }

        logPlaceService.addPlaceToLog(log.getId(),request.getX(),request.getY(),request.getAddress(),request.getRoadAddress(),request.getPlaceName());
        logTextService.addTextToLog(log.getId(),request.getText());

        for (LogImageRequest image : request.getImageUrls()){
            logImageService.addImageToLog(log.getId(),image.getImageData(),image.getItemName());
        }

        for (String tag : request.getTags()){
            logTagService.addTagToLog(log.getId(),tag);
        }

        setIsShow(log, request.getIsShow());
        return log;
    }

    public void setIsShow(Log log, Boolean isShow){
        log.setIsShow(isShow);
        logService.saveLog(log);
    }
}
