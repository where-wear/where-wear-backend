package WhereWear.server.wherewear.log.fashion;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemService;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepository;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogFashionService {
    private final FashionItemService fashionItemService;
    private final LogService logService;
    public Log addFashionItemToLog(Long id, Long categoryId, String itemName) {
        Log log = logService.findByLogId(id);
        FashionItem fashionItem = fashionItemService.addItem(categoryId, itemName);

        log.setFashionItem(fashionItem);

        fashionItemService.saveFashionItem(fashionItem);
        return logService.saveLog(log);
    }

    public Log deleteFashionItemToLog(Long id) {
        Log log = logService.findByLogId(id);
        FashionItem fashionItem = log.getFashionItem();

        if (fashionItem != null) {
            log.removeFashionItem(fashionItem);
            fashionItemService.saveFashionItem(fashionItem);
        }

        return logService.saveLog(log);
    }
}
