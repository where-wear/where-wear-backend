package WhereWear.server.wherewear.log.repository;

import WhereWear.server.wherewear.log.domain.Log;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    Optional<List<Object[]>> countLogsByXY(double xMin, double xMax, double yMin, double yMax);
    Optional<List<Log>> findByXY(double x, double y);
    Optional<List<Log>> nearPlaceLogsByXY(double x, double y);
    Optional<List<Log>> findByUserId(Long userId, Pageable pageable);
    Optional<List<Log>> findLogsByLikedCount(String category);
    Optional<List<Log>> findByUserEmail(String userEmail);
}
