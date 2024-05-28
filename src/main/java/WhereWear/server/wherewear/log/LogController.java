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
    private final LogService logService;
    @PostMapping("/start")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> startLog(@RequestHeader("Authorization") String token){
        User user = userService.findByAccessToken(token);
        Log log = logService.startLog(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }

    @PostMapping("/{logId}/fashionItem/create")
    public ResponseEntity<ApiUtils.ApiResult<LogResponse>> addFashionItemToLog(@PathVariable("logId") Long id,
                                                                               @RequestParam Long categoryId,
                                                                               @RequestParam String itemName){
        Log log = logService.addFashionItemToLog(id,categoryId,itemName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new LogResponse(log)));
    }
}
