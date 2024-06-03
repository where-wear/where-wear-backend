package WhereWear.server.wherewear.log.text;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.log.logImage.LogImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogTextService {
    private final LogService logService;
    public Log addTextToLog(Long logId, String text) {
        Log log = logService.findByLogId(logId);
        log.updateText(text);
        return logService.saveLog(log);
    }

    public Log deleteTextToLog(Long logId) {
        Log log = logService.findByLogId(logId);
        log.updateText(null);
        return logService.saveLog(log);
    }
}
