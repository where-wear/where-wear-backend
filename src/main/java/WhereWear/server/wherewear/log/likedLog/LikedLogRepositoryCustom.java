package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.user.User;

import java.util.Optional;

public interface LikedLogRepositoryCustom {
    Optional<LikedLog> findByLogAndUser(Log log, User user);
}
