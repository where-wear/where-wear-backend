package WhereWear.server.wherewear.likedLog;

import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.user.User;

import java.util.Optional;

public interface LikedLogRepositoryCustom {
    Optional<LikedLog> findByLogAndUser(Log log, User user);
}
