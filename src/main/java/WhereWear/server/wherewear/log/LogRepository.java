package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {
    Optional<Log> findById(Long id);
    Log findByPlace(Place place);
}
