package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.place.LogPlaceService;
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

import java.util.List;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/place")
@Tag(name = "장소 카테고리", description = "장소 카테고리 관리 API")
public class PlaceController {
    private final LogPlaceService logPlaceService;
    @Operation(summary = "장소 관련 로그 수 조회", description = "지정된 장소에 관련된 로그 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 로그 수 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping
    public ResponseEntity<?> getPlaceLogs(@RequestParam("x") double x, @RequestParam("y") double y){
        List<PlaceLogSummary> logSummaries = logPlaceService.processLogs(x,y);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(logSummaries));
    }
}
