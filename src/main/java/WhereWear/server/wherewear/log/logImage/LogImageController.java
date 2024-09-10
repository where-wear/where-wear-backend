package WhereWear.server.wherewear.log.logImage;

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

import java.io.IOException;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/log")
@Tag(name = "로그 이미지", description = "로그 이미지 관리 API")
public class LogImageController {

    private final LogImageService logImageService;

    @Operation(summary = "이미지 추가", description = "지정된 로그에 이미지를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이미지 추가 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/{logId}/image/create")
    public ResponseEntity<?> addImageToLog(
            @Parameter(description = "로그 ID") @PathVariable("logId") Long logId,
            @Parameter(description = "이미지 요청 데이터") @RequestBody LogImageRequest request) throws IOException {

        Log log = logImageService.addImageToLog(logId, request.getFile());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @Operation(summary = "이미지 삭제", description = "지정된 로그에서 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 삭제 성공",
                    content = @Content(schema = @Schema(implementation = LogResponse.class))),
            @ApiResponse(responseCode = "404", description = "로그 또는 이미지를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PutMapping("/{logId}/image/{imageId}/delete")
    public ResponseEntity<?> deleteImageToLog(
            @Parameter(description = "로그 ID") @PathVariable("logId") Long logId,
            @Parameter(description = "이미지 ID") @PathVariable("imageId") Long imageId) {

        Log log = logImageService.deleteImageToLog(logId, imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(success(new LogResponse(log)));
    }
}
