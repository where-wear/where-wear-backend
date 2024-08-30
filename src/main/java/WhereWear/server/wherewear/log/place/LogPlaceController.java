package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.fashion.LogFashionService;
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
public class LogPlaceController {
    private final LogPlaceService logPlaceService;
    @PostMapping("/{logId}/place/create")
    public ResponseEntity<?> addFashionItemToLog(@PathVariable("logId") Long logId,
                                                                               @RequestParam Double x,
                                                                               @RequestParam Double y,
                                                                               @RequestParam String roadAddress,
                                                                               @RequestParam String address,
                                                                               @RequestParam String placeName){
        Log log = logPlaceService.addPlaceToLog(logId,x,y,roadAddress,address,placeName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PutMapping("/{logId}/place/delete")
    public ResponseEntity<?> deleteFashionItemToLog(@PathVariable("logId") Long logId){
        Log log = logPlaceService.deletePlaceToLog(logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
