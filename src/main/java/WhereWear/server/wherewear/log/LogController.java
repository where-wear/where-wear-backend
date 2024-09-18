package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItemRequest;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/log")
@Tag(name = "로그", description = "로그 관리 API")
public class LogController {

    private final UserService userService;
    private final CreateLogService createLogService;
    private final LogService logService;

    @Operation(summary = "로그 생성", description = "새로운 로그를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그 생성 성공",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultSuccess.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/createLog")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token,
                                    @RequestParam("text") String text,
                                    @RequestParam("items") List<FashionItemRequest> items,
                                    @RequestParam("x") Double x,
                                    @RequestParam("y") Double y,
                                    @RequestParam("roadAddress") String roadAddress,
                                    @RequestParam("address") String address,
                                    @RequestParam("placeName") String placeName,
                                    @RequestParam("isShow") boolean isShow,
                                    @RequestParam("tags") List<String> tags,
                                    @RequestPart(value = "imageUrls", required = false) List<MultipartFile> imageUrls) throws IOException {
        try {
            User user = userService.findByAccessToken(token);
            Log log = createLogService.create(user.getEmail(), text, imageUrls, items, x, y, roadAddress, address, placeName, isShow, tags);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiUtils.success(new LogResponse(log)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @Operation(summary = "로그 삭제", description = "기존 로그를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultSuccess.class))),
            @ApiResponse(responseCode = "404", description = "로그를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @DeleteMapping("/{logId}/delete")
    public ResponseEntity<?> deleteLog(@PathVariable("logId") Long logId) {
        try {
            logService.deleteLog(logId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiUtils.success(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
}
