package WhereWear.server.wherewear.log.service;

import WhereWear.server.wherewear.likedLog.LikedLog;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.repository.LogRepository;
import WhereWear.server.wherewear.log.dto.LogResponse;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.util.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;
    private final UserService userService;


    public LogResponse findUserLog(Long id, String userEmail){
        Log log = findByLogId(id);
        LogResponse logResponse = new LogResponse(log);

        if(userEmail != null){
            User user = userService.findByEmail(userEmail);

            if(log.getUser().equals(user)){
                logResponse.updateIsMyLog(true);
            }

            for(LikedLog likedLog : log.getLikedLogs()){
                if(likedLog.getUser().equals(user)){
                    logResponse.updateIsLike(true);
                }
            }
        }

        return logResponse;
    }

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

    public List<Log> findLogsByUserId(Long userId){
        return logRepository.findByUserId(userId)
                .map(logs -> {
                    List<Log> last10Logs = logs.stream()
                            .skip(Math.max(0, logs.size() - 5)) // 마지막 10개 선택
                            .collect(Collectors.toList());
                    return ListUtils.reverseList(last10Logs); // 뒤집기 적용
                })
                .orElse(Collections.emptyList());
    }

    public List<Log> findLogsByUserEmail(String userEmail){
        return logRepository.findByUserEmail(userEmail)
                .map(ListUtils::reverseList)
                .orElse(Collections.emptyList());
    }

    public List<Object[]> countLogsByXY(double xMin, double xMax, double yMin, double yMax){
        return logRepository.countLogsByXY(xMin, xMax, yMin, yMax)
                .map(ListUtils::reverseList)
                .orElse(Collections.emptyList());
    }

    public List<Log> findByXY(double x, double y){
        return logRepository.findByXY(x,y)
                .map(ListUtils::reverseList)
                .orElse(Collections.emptyList());
    }

    public List<Log> nearPlaceLogsByXY(double x, double y){
        return logRepository.nearPlaceLogsByXY(x,y)
                .map(ListUtils::reverseList)
                .orElse(Collections.emptyList());
    }

    public Log findByPlace(Place place){
        return logRepository.findByPlace(place);
    }
}
