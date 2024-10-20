package WhereWear.server.wherewear.log.recommend;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.log.place.PlaceDto;
import WhereWear.server.wherewear.place.Place;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recommend")
@Tag(name = "추천 로그", description = "추천 로그 API")
public class RecommendController {
    private final LogRecommendService logRecommendService;

    @Operation(summary = "추천 로그 조회", description = "추천 로그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "추천 로그 조회 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "요청에 대한 응답을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping
    public ResponseEntity<?> recommendLogs(){
        List<Log> logs = logRecommendService.getRecommendLogs();

        List<LogResponse> response = logs.stream()
                .map(log -> new LogResponse(log))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }
}
