package WhereWear.server.wherewear.log.fashion;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.LogService;
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
@Tag(name = "패션 아이템", description = "패션 아이템 관리 API")
public class LogFashionController {
    private final LogFashionService logFashionService;

    @Operation(summary = "패션 아이템 추가", description = "지정된 로그에 패션 아이템을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "패션 아이템 추가 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/{logId}/fashionItem/create")
    public ResponseEntity<?> addFashionItemToLog(
            @Parameter(description = "로그 ID") @PathVariable("logId") Long id,
            @Parameter(description = "카테고리 ID") @RequestParam Long categoryId,
            @Parameter(description = "패션 아이템 이름") @RequestParam String itemName) {

        Log log = logFashionService.addFashionItemToLog(id, categoryId, itemName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
