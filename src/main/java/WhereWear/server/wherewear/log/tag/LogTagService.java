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

    public Log addTagToLog(Long logId, String tagName) {
        Log log = logService.findByLogId(logId);
        Tag tag = tagService.addTag(tagName);

        log.setTags(tag);

        return logService.saveLog(log);
    }

}
