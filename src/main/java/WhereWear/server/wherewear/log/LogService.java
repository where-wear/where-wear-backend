package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<List<Object[]>> countLogsByXY(double xMin, double xMax, double yMin, double yMax){
        return logRepository.countLogsByXY(xMin, xMax, yMin, yMax);
    }
}
