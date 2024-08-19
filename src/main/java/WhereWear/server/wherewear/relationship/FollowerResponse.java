package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@RequiredArgsConstructor
public class FollowerResponse {
    private List<UserDto> followers = new ArrayList<>();

    public FollowerResponse(List<User> followerList) {
        for (User user : followerList) {
            this.followers.add(new UserDto(user));
        }
    }
}
