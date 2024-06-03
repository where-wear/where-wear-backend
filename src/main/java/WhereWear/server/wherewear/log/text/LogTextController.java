package WhereWear.server.wherewear.log.text;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
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
public class LogTextController {
    private final LogTextService logTextService;
    @PostMapping("/{logId}/text/create")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> addTextToLog(@PathVariable("logId") Long logId,
                                                                         @RequestParam String text){
        Log log = logTextService.addTextToLog(logId,text);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PutMapping("/{logId}/text/delete")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> deleteTextToLog(@PathVariable("logId") Long logId){
        Log log = logTextService.deleteTextToLog(logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

}
