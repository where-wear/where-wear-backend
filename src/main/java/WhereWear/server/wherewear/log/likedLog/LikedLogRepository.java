package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.refreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedLogRepository extends JpaRepository<LikedLog, Long> {
}
