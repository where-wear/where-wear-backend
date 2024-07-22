package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.user.account.dto.NicknameCheckResponse;
import WhereWear.server.wherewear.util.ApiUtils;
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
public class LogController {
    private final UserService userService;
    private final CreateLogService createLogService;
    private final LogService logService;
    @PostMapping("/createLog")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> create(@RequestHeader("Authorization") String token, @RequestBody LogRequest request){
        User user = userService.findByAccessToken(token);
        Log log = createLogService.create(user.getNickname(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @DeleteMapping("/{logId}/delete")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> deleteLog(@PathVariable("logId") Long logId){
        logService.deleteLog(logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(null));
    }
}
