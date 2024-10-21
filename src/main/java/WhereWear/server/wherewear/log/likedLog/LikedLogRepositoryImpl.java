package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class LikedLogRepositoryImpl implements LikedLogRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LikedLog> findByLogAndUser(Log log, User user) {
        String queryStr = "SELECT l FROM LikedLog l " +
                "WHERE l.log = :log AND l.user = :user";

        TypedQuery<LikedLog> query = entityManager.createQuery(queryStr, LikedLog.class);
        query.setParameter("log", log);
        query.setParameter("user", user);

        try {
            LikedLog result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
