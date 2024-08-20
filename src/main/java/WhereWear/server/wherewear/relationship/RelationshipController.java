package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.fashion.category.dto.CategoryResponse;
import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.log.LogResponse;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import WhereWear.server.wherewear.user.UserService;
import WhereWear.server.wherewear.util.ApiUtils;
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
public class RelationshipController {
    private final RelationshipService relationshipService;
    private final UserService userService;
    @PostMapping("/follow/{userId}")
    public ResponseEntity followUser(@RequestHeader("Authorization") String token, @PathVariable("userId") Long userId){
        User fromUser = userService.findByAccessToken(token);
        relationshipService.followUser(fromUser.getEmail(), userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity unfollowUser(@RequestHeader("Authorization") String token, @PathVariable("userId") Long userId){
        User fromUser = userService.findByAccessToken(token);
        relationshipService.unfollowUser(fromUser.getEmail(), userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{userId}/followers")
    public ResponseEntity<ApiUtils.ApiResult<FollowerResponse>> getFollowers(@PathVariable("userId") Long userId){
        List<User> followerList = relationshipService.getFollowers(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new FollowerResponse(followerList)));
    }

    @GetMapping("{userId}/followings")
    public ResponseEntity<ApiUtils.ApiResult<FollowingResponse>> getFollowings(@PathVariable("userId") Long userId){
        List<User> followingList = relationshipService.getFollowings(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(new FollowingResponse(followingList)));
    }
}
