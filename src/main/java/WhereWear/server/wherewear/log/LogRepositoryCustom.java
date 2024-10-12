package WhereWear.server.wherewear.log;

import java.util.List;
import java.util.Optional;

public interface LogRepositoryCustom {
    Optional<List<Object[]>> countLogsByXY(double xMin, double xMax, double yMin, double yMax);
    Optional<List<Log>> findByXY(double x, double y);
    Optional<List<Log>> nearPlaceLogsByXY(double x, double y);
    Optional<List<Log>> findByUserId(Long userId);
}
