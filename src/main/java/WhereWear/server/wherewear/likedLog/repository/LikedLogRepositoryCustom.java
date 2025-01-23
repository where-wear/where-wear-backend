package WhereWear.server.wherewear.likedLog.repository;

import WhereWear.server.wherewear.likedLog.domain.LikedLog;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.user.User;

import java.util.Optional;

public interface LikedLogRepositoryCustom {
    Optional<LikedLog> findByLogAndUser(Log log, User user);
}
