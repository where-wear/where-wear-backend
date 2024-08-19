package WhereWear.server.wherewear.relationship;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.user.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "relationship")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
