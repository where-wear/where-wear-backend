package WhereWear.server.wherewear.log.fashion;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemService;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.log.tag.LogTag;
import WhereWear.server.wherewear.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogFashionService {
    private final LogFashionItemRepository logFashionItemRepository;
    private final FashionItemService fashionItemService;
    private final LogService logService;

    public Log addFashionItemToLog(Long logId, Long categoryId, String fashionItemName) {
        Log log = logService.findByLogId(logId);
        FashionItem fashionItem = fashionItemService.addItem(categoryId, fashionItemName);

        createLogFashionItem(log,fashionItem);

        return logService.saveLog(log);
    }

    public Log deleteFashionItemToLog(Long logId, Long fashionItemId) {
        Log log = logService.findByLogId(logId);
        FashionItem fashionItem = fashionItemService.findFashionItemById(fashionItemId);

        if (fashionItem != null) {
            LogFashion logFashion = findLogFashion(logId,fashionItemId);
            deleteLogFashion(logFashion.getId());
        }

        return logService.saveLog(log);
    }

    public LogFashion createLogFashionItem(Log log, FashionItem fashionItem){
        LogFashion logFashion = new LogFashion();
        logFashion.setLog(log);
        logFashion.setFashionItem(fashionItem);

        return logFashionItemRepository.save(logFashion);
    }

    public LogFashion findLogFashion(Long logId, Long fashionItemId){
        return logFashionItemRepository.findLogFashion(logId, fashionItemId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected LogFashion"));
    }

    public void deleteLogFashion(Long fashionItemId){
        logFashionItemRepository.delete(fashionItemId);
    }
}
