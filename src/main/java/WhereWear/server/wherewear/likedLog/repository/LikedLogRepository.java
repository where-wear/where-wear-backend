package WhereWear.server.wherewear.likedLog.repository;

import WhereWear.server.wherewear.likedLog.domain.LikedLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikedLogRepository extends JpaRepository<LikedLog, Long>, LikedLogRepositoryCustom {
    @Query("SELECT DISTINCT ll FROM LikedLog ll " +
            "JOIN FETCH ll.log l " +
            "JOIN FETCH l.place p " +
            "JOIN FETCH l.user u " +
            "LEFT JOIN FETCH l.logImages li "+
            "WHERE ll.user.email = :email")
    Optional<List<LikedLog>> findLikedLogs(@Param("email") String email);
}
