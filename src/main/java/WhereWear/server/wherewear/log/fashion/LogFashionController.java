package WhereWear.server.wherewear.log.fashion;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.LogService;
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
public class LogFashionController {
    private final LogFashionService logFashionService;
    @PostMapping("/{logId}/fashionItem/create")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> addFashionItemToLog(@PathVariable("logId") Long id,
                                                                               @RequestParam Long categoryId,
                                                                               @RequestParam String itemName){
        Log log = logFashionService.addFashionItemToLog(id,categoryId,itemName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PutMapping("/{logId}/fashionItem/delete")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> deleteFashionItemToLog(@PathVariable("logId") Long logId){
        Log log = logFashionService.deleteFashionItemToLog(logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
