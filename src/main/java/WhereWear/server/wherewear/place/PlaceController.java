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
import java.util.stream.Collectors;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/place")
@Tag(name = "장소 카테고리", description = "장소 카테고리 관리 API")
public class PlaceController {
    private final LogPlaceService logPlaceService;
    @Operation(summary = "장소 관련 로그 수 조회", description = "장소에 관련된 로그 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "장소 로그 수 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping
    public ResponseEntity<?> countLogsByXY(@RequestParam("minX") double minX,
                                          @RequestParam("maxX") double maxX,
                                          @RequestParam("minY") double minY,
                                          @RequestParam("maxY") double maxY){
        List<Object[]> logCounts = logPlaceService.countLogsByXY(minX, maxX, minY, maxY);
        List<LogCountDto> response = logCounts.stream()
                .map(arr -> new LogCountDto((Double) arr[0], (Double) arr[1], (Long) arr[2]))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(response));
    }

    @Operation(summary = "특정 장소 로그 조회", description = "지정된 장소에 관련된 로그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "특정 장소 로그 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/detail")
    public ResponseEntity<?> countLogsByXY(@RequestParam("x") double x,
                                           @RequestParam("y") double y){
        List<Log> logs = logPlaceService.findLogsByXY(x,y);
        List<LogResponse> response = logs.stream()
                .map(log -> new LogResponse(log)) // Log 객체를 기반으로 LogResponse 생성
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(response));
    }

    @Operation(summary = "근처 장소 로그 조회", description = "근처 장소에 관련된 로그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "근처 장소 로그 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/nearPlace")
    public ResponseEntity<?> nearPlaceLogsByXY(@RequestParam("x") double x,
                                           @RequestParam("y") double y){
        List<Log> logs = logPlaceService.nearPlaceLogsByXY(x,y);
        List<LogResponse> response = logs.stream()
                .map(log -> new LogResponse(log))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(response));
    }

}
