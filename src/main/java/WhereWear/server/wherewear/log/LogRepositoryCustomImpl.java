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
    public Optional<List<Object[]>> countLogsByXY(double xMin, double xMax, double yMin, double yMax) {
        String queryStr = "SELECT l.place.x, l.place.y, COUNT(l) " +
                "FROM Log l " +
                "WHERE l.place.x BETWEEN :xMin AND :xMax " +
                "AND l.place.y BETWEEN :yMin AND :yMax " +
                "GROUP BY l.place.x, l.place.y";

        TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);
        query.setParameter("xMin", xMin);
        query.setParameter("xMax", xMax);
        query.setParameter("yMin", yMin);
        query.setParameter("yMax", yMax);

        List<Object[]> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

}
