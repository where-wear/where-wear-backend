package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.fashion.LogFashionService;
import WhereWear.server.wherewear.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/log")
@Tag(name = "로그 장소", description = "로그 장소 관리 API")
public class LogPlaceController {
    private final LogPlaceService logPlaceService;

    @Operation(summary = "로그 장소 추가", description = "지정된 로그에 장소를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 추가 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/{logId}/place/create")
    public ResponseEntity<?> addPlaceToLog(@PathVariable("logId") Long logId,
                                                                               @RequestParam Double x,
                                                                               @RequestParam Double y,
                                                                               @RequestParam String roadAddress,
                                                                               @RequestParam String address,
                                                                               @RequestParam String placeName){
        Log log = logPlaceService.addPlaceToLog(logId,x,y,roadAddress,address,placeName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @Operation(summary = "로그 장소 삭제", description = "지정된 로그에 장소를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 삭제 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PutMapping("/{logId}/place/delete")
    public ResponseEntity<?> deletePlaceToLog(@PathVariable("logId") Long logId){
        Log log = logPlaceService.deletePlaceToLog(logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
