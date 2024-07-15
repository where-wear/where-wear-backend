package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.fashion.category.service.CategoryService;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import WhereWear.server.wherewear.log.fashion.LogFashionService;
import WhereWear.server.wherewear.log.likedLog.LikedLog;
import WhereWear.server.wherewear.log.logImage.LogImage;
import WhereWear.server.wherewear.log.logImage.LogImageRequest;
import WhereWear.server.wherewear.log.logImage.LogImageService;
import WhereWear.server.wherewear.log.place.LogPlaceService;
import WhereWear.server.wherewear.log.savedLog.SavedLog;
import WhereWear.server.wherewear.log.tag.LogTagService;
import WhereWear.server.wherewear.log.text.LogTextService;
import WhereWear.server.wherewear.log.user.LogUserService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.Tag;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final LogRepository logRepository;

    public Log startLog() {
        Log log = new Log();
        return saveLog(log);
    }

    public void deleteLog(Long logId) {
        Log log = findByLogId(logId);
        logRepository.delete(log);
    }

    public Log saveLog(Log log){
        return logRepository.save(log);
    }

    public Log findByLogId(Long id){
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected log"));
    }

}
