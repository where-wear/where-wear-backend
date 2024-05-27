package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.config.jwt.TokenProvider;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    public Log startLog(String userEmail) {
        Log log = new Log();
        User user = userService.findByEmail(userEmail);
        log.setStatus("inProgress");
        log.setUser(user);
        user.addLog(log);
        userRepository.save(user);
        Log savedLog = logRepository.save(log);
        return savedLog;
    }
}
