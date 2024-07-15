package WhereWear.server.wherewear.log.user;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogUserService {
    private final UserService userService;
    private final LogService logService;
    public Log addUserToLog(Long logId, String email) {
        Log log = logService.findByLogId(logId);
        User user = userService.findByEmail(email);
        log.setUser(user);

        userService.saveUser(user);
        return logService.saveLog(log);
    }
}
