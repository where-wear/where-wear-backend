package WhereWear.server.wherewear.log;

import WhereWear.server.wherewear.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
