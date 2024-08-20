package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FollowingResponse {
    private List<UserDto> followings = new ArrayList<>();

    public FollowingResponse(List<User> followingList) {
        for (User user : followingList) {
            this.followings.add(new UserDto(user));
        }
    }
}
