package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;
    private final UserService userService;

    public Log startLog(String email) {
        User user = userService.findByEmail(email);
        Log log = new Log(user);
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
