package WhereWear.server.wherewear.log.text;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "로그 텍스트", description = "로그에 텍스트 추가 및 삭제 API")
public class LogTextController {

    private final LogTextService logTextService;

    @Operation(summary = "텍스트 추가", description = "지정된 로그에 새로운 텍스트를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "텍스트 추가 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/{logId}/text/create")
    public ResponseEntity<?> addTextToLog(
            @Parameter(description = "로그 ID", required = true) @PathVariable("logId") Long logId,
            @Parameter(description = "추가할 텍스트", required = true) @RequestParam String text) {

        Log log = logTextService.addTextToLog(logId, text);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @Operation(summary = "텍스트 삭제", description = "지정된 로그에서 텍스트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "텍스트 삭제 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PutMapping("/{logId}/text/delete")
    public ResponseEntity<?> deleteTextToLog(
            @Parameter(description = "로그 ID", required = true) @PathVariable("logId") Long logId) {

        Log log = logTextService.deleteTextToLog(logId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(success(new LogResponse(log)));
    }
}
