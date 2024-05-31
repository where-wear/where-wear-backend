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
    public Log startLog(String userEmail) {
        User user = userService.findByEmail(userEmail);

        Log log = new Log();
        log.setStatus("inProgress");
        log.setUser(user);

        userService.saveUser(user);
        return saveLog(log);
    }

    public Log saveLog(Log log){
        return logRepository.save(log);
    }

    public Log findByLogId(Long id){
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected log"));
    }

}
