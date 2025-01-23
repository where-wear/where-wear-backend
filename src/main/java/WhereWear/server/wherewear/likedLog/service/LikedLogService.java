package WhereWear.server.wherewear.likedLog.service;

import WhereWear.server.wherewear.likedLog.domain.LikedLog;
import WhereWear.server.wherewear.likedLog.dto.LikedDto;
import WhereWear.server.wherewear.likedLog.repository.LikedLogRepository;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.repository.LogRepository;
import WhereWear.server.wherewear.log.service.LogService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.util.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikedLogService {
    private final LogService logService;
    private final UserService userService;
    private final LikedLogRepository likedLogRepository;
    private final LogRepository logRepository;

    public LikedDto setLikedLog(String email, Long logId) {
        Log log = logService.findByLogId(logId);
        User user = userService.findByEmail(email);

        Optional<LikedLog> existingLikedLog = likedLogRepository.findByLogAndUser(log, user);

        if (existingLikedLog.isPresent()) {
            LikedLog likedLog = existingLikedLog.get();
            likedLogRepository.delete(likedLog);

            log.removeLikedLog(likedLog);
            logService.saveLog(log);

            user.removeLikedLog(likedLog);
            userService.saveUser(user);

            return new LikedDto(log, false, user);
        }

        LikedLog likedLog = likedLogRepository.save(new LikedLog(log, user));

        log.setLikedLogs(likedLog);
        logService.saveLog(log);

        user.setLikedLogs(likedLog);
        userService.saveUser(user);

        return new LikedDto(log, true, user);
    }

    public List<LikedLog> getUserLikedLog(String email) {
        return likedLogRepository.findLikedLogs(email)
                .map(ListUtils::reverseList)
                .orElse(Collections.emptyList());
    }

    public List<Log> getTopLogs(String category) {
        return logRepository.findLogsByLikedCount(category)
                .orElse(Collections.emptyList());
    }
}
