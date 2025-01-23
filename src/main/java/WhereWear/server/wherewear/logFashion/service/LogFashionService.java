package WhereWear.server.wherewear.logFashion.service;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemService;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.service.LogService;
import WhereWear.server.wherewear.logFashion.repository.LogFashionItemRepository;
import WhereWear.server.wherewear.logFashion.domain.LogFashion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogFashionService {
    private final LogFashionItemRepository logFashionItemRepository;
    private final FashionItemService fashionItemService;
    private final LogService logService;

    public Log deleteFashionItemToLog(Long logId, Long fashionItemId) {
        Log log = logService.findByLogId(logId);
        FashionItem fashionItem = fashionItemService.findFashionItemById(fashionItemId);

        if (fashionItem != null) {
            LogFashion logFashion = findLogFashion(logId,fashionItemId);
            deleteLogFashion(logFashion.getId());
        }

        return logService.saveLog(log);
    }

    public LogFashion findLogFashion(Long logId, Long fashionItemId){
        return logFashionItemRepository.findLogFashion(logId, fashionItemId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected LogFashion"));
    }

    public void deleteLogFashion(Long fashionItemId){
        logFashionItemRepository.delete(fashionItemId);
    }
}
