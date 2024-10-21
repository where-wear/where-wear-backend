package WhereWear.server.wherewear.log.likedLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedLogRepository extends JpaRepository<LikedLog, Long>, LikedLogRepositoryCustom {
}
