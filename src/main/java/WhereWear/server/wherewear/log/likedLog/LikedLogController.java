package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.log.place.PlaceDto;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.util.ApiUtils;
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
@RequestMapping("/api/liked")
@Tag(name = "좋아한 로그", description = "좋아한 로그 관리 API")
public class LikedLogController {
    private final UserService userService;
    private final LikedLogService likedLogService;
    @PostMapping
    public ResponseEntity<?> setLikedLog(@RequestHeader("Authorization") String token,
                                         @RequestParam("logId") Long logId){
        User user = userService.findByAccessToken(token);
        likedLogService.setLikedLog(user.getEmail(),logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success("좋아요를 눌렀습니다."));
    }

    @GetMapping
    public ResponseEntity<?> getUserLikedLog(@RequestParam("userId") Long userId){
        List<Log> logs = likedLogService.getUserLikedLog(userId);
        List<LogResponse> response = logs.stream()
                .map(log -> new LogResponse(log))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }
}
