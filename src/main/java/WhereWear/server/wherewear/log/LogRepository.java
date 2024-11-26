package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {
    Optional<Log> findById(Long id);
    Log findByPlace(Place place);

    @Query("SELECT l FROM Log l " +
            "WHERE l.place.category = :gu " +
            "AND l.user.height BETWEEN :minHeight AND :maxHeight " +
            "AND l.user.weight BETWEEN :minWeight AND :maxWeight ")
    Optional<List<Log>> findRecommendLogs(@Param("gu") String gu,
                                          @Param("minHeight") int minHeight,
                                          @Param("maxHeight") int maxHeight,
                                          @Param("minWeight") int minWeight,
                                          @Param("maxWeight") int maxWeight);
}
