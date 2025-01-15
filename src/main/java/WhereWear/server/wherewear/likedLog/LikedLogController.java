package WhereWear.server.wherewear.likedLog;

import WhereWear.server.wherewear.likedLog.dto.LikedDto;
import WhereWear.server.wherewear.likedLog.dto.LikedLogResponse;
import WhereWear.server.wherewear.log.dto.LogResponse;
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
        LikedDto likedDto = likedLogService.setLikedLog(user.getEmail(),logId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(likedDto));
    }

    @GetMapping
    public ResponseEntity<?> getUserLikedLog(@RequestHeader("Authorization") String token){
        User user = userService.findByAccessToken(token);

        List<LikedLog> logs = likedLogService.getUserLikedLog(user.getEmail());

        List<LikedLogResponse> response = logs.stream()
                .map(log -> new LikedLogResponse(log))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }
}
