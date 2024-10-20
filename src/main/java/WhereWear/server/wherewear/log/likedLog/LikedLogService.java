package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LikedLogService {
    private final LogService logService;
    private final UserService userService;
    private final LikedLogRepository likedLogRepository;
    public void setLikedLog(String email, Long logId){
        Log log = logService.findByLogId(logId);
        User user = userService.findByEmail(email);

        Optional<LikedLog> existingLikedLog = likedLogRepository.findByLogAndUser(log, user);

        if (!existingLikedLog.isPresent()) {
            LikedLog likedLog = likedLogRepository.save(new LikedLog(log,user));

            log.setLikedLogs(likedLog);
            logService.saveLog(log);

            user.setLikedLogs(likedLog);
            userService.saveUser(user);
        }
    }
    public List<Log> getUserLikedLog(Long userId){
        User user = userService.findById(userId);
        return user.getLikedLogs().stream()
                .map(LikedLog::getLog)
                .collect(Collectors.toList());
    }
}
