package WhereWear.server.wherewear.log.tag;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.place.LogPlaceService;
import WhereWear.server.wherewear.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/log")
public class LogTagController {

    private final LogTagService logTagService;

    @PostMapping("/{logId}/tag/create")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> addTagToLog(@PathVariable("logId") Long logId,
                                                                               @RequestParam String tagName){
        Log log = logTagService.addTagToLog(logId,tagName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PutMapping("/{logId}/tag/{tagId}/delete")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> deleteTagToLog(@PathVariable("logId") Long logId,
                                                                          @PathVariable("tagId") Long tagId){
        Log log = logTagService.deleteTagToLog(logId,tagId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
