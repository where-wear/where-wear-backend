package WhereWear.server.wherewear.log;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class LogRepositoryCustomImpl implements LogRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Log>> findLogsByXYRange(double xMin, double xMax, double yMin, double yMax) {
        String queryStr = "SELECT l FROM Log l WHERE l.place.x BETWEEN :xMin AND :xMax AND l.place.y BETWEEN :yMin AND :yMax";

        TypedQuery<Log> query = entityManager.createQuery(queryStr, Log.class);
        query.setParameter("xMin", xMin);
        query.setParameter("xMax", xMax);
        query.setParameter("yMin", yMin);
        query.setParameter("yMax", yMax);

        List<Log> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

}
