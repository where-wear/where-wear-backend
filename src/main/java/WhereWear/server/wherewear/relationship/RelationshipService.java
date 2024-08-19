package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RelationshipService {
    private final UserService userService;
    private final RelationshipRepository relationshipRepository;

    @Transactional
    public boolean followUser(String fromUserEmail, Long toUserId) {

        User fromUser = userService.findByEmail(fromUserEmail);
        User toUser = userService.findById(toUserId);

        if (fromUser.equals(toUser)) {
            throw new IllegalArgumentException("Cannot follow yourself.");
        }

        if (relationshipRepository.existsByFollowerAndFollowing(fromUser, toUser)) {
            throw new IllegalArgumentException("Already following this user.");
        }

        Relationship relationship = new Relationship();
        relationship.setFollower(fromUser);
        relationship.setFollowing(toUser);

        relationshipRepository.save(relationship);

        return true;
    }

    public List<User> getFollower(Long userId){

        User user = userService.findById(userId);

        return user.getFollowers();

    }

}
