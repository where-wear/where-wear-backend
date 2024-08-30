package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
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

import java.util.List;

import static WhereWear.server.wherewear.util.ApiUtils.success;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/accounts")
@Tag(name = "팔로우/팔로잉", description = "사용자 간의 팔로우 및 팔로워 관리 API")
public class RelationshipController {

    private final RelationshipService relationshipService;
    private final UserService userService;

    @Operation(summary = "사용자 팔로우", description = "특정 사용자를 팔로우합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> followUser(
            @Parameter(description = "JWT 인증 토큰", required = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "팔로우할 사용자 ID", required = true) @PathVariable("userId") Long userId) {

        User fromUser = userService.findByAccessToken(token);
        relationshipService.followUser(fromUser.getEmail(), userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 언팔로우", description = "특정 사용자의 팔로우를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "언팔로우 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity<?> unfollowUser(
            @Parameter(description = "JWT 인증 토큰", required = true) @RequestHeader("Authorization") String token,
            @Parameter(description = "언팔로우할 사용자 ID", required = true) @PathVariable("userId") Long userId) {

        User fromUser = userService.findByAccessToken(token);
        relationshipService.unfollowUser(fromUser.getEmail(), userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "팔로워 목록 조회", description = "특정 사용자의 팔로워 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = FollowerResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> getFollowers(
            @Parameter(description = "조회할 사용자 ID", required = true) @PathVariable("userId") Long userId) {

        List<User> followerList = relationshipService.getFollowers(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new FollowerResponse(followerList)));
    }

    @Operation(summary = "팔로잉 목록 조회", description = "특정 사용자의 팔로잉 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로잉 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = FollowingResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ApiUtils.ApiResultError.class)))
    })
    @GetMapping("/{userId}/followings")
    public ResponseEntity<?> getFollowings(
            @Parameter(description = "조회할 사용자 ID", required = true) @PathVariable("userId") Long userId) {

        List<User> followingList = relationshipService.getFollowings(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new FollowingResponse(followingList)));
    }
}
