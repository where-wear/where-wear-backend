package WhereWear.server.wherewear.log.tag;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.tag.Tag;
import WhereWear.server.wherewear.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class LogTagService {

    private final LogService logService;
    private final TagService tagService;
    private final LogTagRepository logTagRepository;

    public Log addTagToLog(Long logId, String tagName) {
        Log log = logService.findByLogId(logId);
        Tag tag = tagService.addTag(tagName);

        createLogTag(log,tag);

        return logService.saveLog(log);
    }

    public Log deleteTagToLog(Long logId, Long tagId) {
        Log log = logService.findByLogId(logId);
        Tag tag = tagService.findTagById(tagId);

        if (tag != null) {
            LogTag logTag = findLogTag(logId,tagId);
            deleteLogTag(logTag.getId());
        }

        return logService.saveLog(log);
    }

    public LogTag createLogTag(Log log, Tag tag){
        LogTag logTag = new LogTag();
        logTag.setLog(log);
        logTag.setTag(tag);

        return logTagRepository.save(logTag);
    }

    public LogTag findLogTag(Long logId, Long tagId){
        return logTagRepository.findLogTag(logId,tagId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected logTag"));
    }

    public void deleteLogTag(Long logTagId){
        logTagRepository.deleteById(logTagId);
    }
}
