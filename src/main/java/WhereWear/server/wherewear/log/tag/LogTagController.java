package WhereWear.server.wherewear.log.tag;

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
@Tag(name = "태그", description = "로그에 태그 추가 및 삭제 API")
public class LogTagController {

    private final LogTagService logTagService;

    @Operation(summary = "태그 추가", description = "지정된 로그에 새로운 태그를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "태그 추가 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/{logId}/tag/create")
    public ResponseEntity<?> addTagToLog(
            @Parameter(description = "로그 ID", required = true) @PathVariable("logId") Long logId,
            @Parameter(description = "추가할 태그 이름", required = true) @RequestParam String tagName) {

        Log log = logTagService.addTagToLog(logId, tagName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @Operation(summary = "태그 삭제", description = "지정된 로그에서 태그를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그 삭제 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "404", description = "로그 또는 태그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PutMapping("/{logId}/tag/{tagId}/delete")
    public ResponseEntity<?> deleteTagToLog(
            @Parameter(description = "로그 ID", required = true) @PathVariable("logId") Long logId,
            @Parameter(description = "삭제할 태그 ID", required = true) @PathVariable("tagId") Long tagId) {

        Log log = logTagService.deleteTagToLog(logId, tagId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(success(new LogResponse(log)));
    }
}
