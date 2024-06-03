package WhereWear.server.wherewear.log.logImage;

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
public class LogImageController {
    private final LogImageService logImageService;
    @PostMapping("/{logId}/image/create")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> addImageToLog(@PathVariable("logId") Long logId,
                                                                               @RequestParam String imageUrl){
        Log log = logImageService.addImageToLog(logId,imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PutMapping("/{logId}/image/{imageId}/delete")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> deleteImageToLog(@PathVariable("logId") Long logId,
                                                                            @PathVariable("imageId") Long imageId){
        Log log = logImageService.deleteImageToLog(logId,imageId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
