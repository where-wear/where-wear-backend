package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRepository;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItemService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogService {
    private final LogRepository logRepository;
    private final UserService userService;
    private final FashionItemService fashionItemService;
    public Log startLog(String userEmail) {
        Log log = new Log();
        User user = userService.findByEmail(userEmail);
        log.setStatus("inProgress");
        log.setUser(user);
        user.addLog(log);
        userService.saveUser(user);
        Log savedLog = saveLog(log);
        return savedLog;
    }

    public Log addFashionItemToLog(Long id, Long categoryId, String itemName) {
        Log log = findByLogId(id);
        FashionItem fashionItem = fashionItemService.addItem(categoryId, itemName);
        log.setFashionItem(fashionItem);
        fashionItem.addLog(log);
        fashionItemService.saveFashionItem(fashionItem);
        Log savedLog = saveLog(log);
        return savedLog;
    }

    public Log deleteFashionItemToLog(Long id) {
        Log log = findByLogId(id);
        if (log == null) {
            throw new IllegalArgumentException("Log not found for id: " + id);
        }
        FashionItem fashionItem = log.getFashionItem();
        if (fashionItem != null) {
            log.removeFashionItem(fashionItem);
            fashionItem.getLogs().remove(log);
            fashionItemService.saveFashionItem(fashionItem);
        }
        Log savedLog = saveLog(log);
        return savedLog;
    }

    public Log saveLog(Log log){
        return logRepository.save(log);
    }

    public Log findByLogId(Long id){
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected log"));
    }

}
