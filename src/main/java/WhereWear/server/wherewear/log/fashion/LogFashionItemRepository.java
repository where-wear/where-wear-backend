package WhereWear.server.wherewear.log.fashion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LogFashionItemRepository {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public LogFashion save(LogFashion logFashion) {
        em.persist(logFashion);
        return logFashion;
    }

    @Transactional
    public void delete(Long id){
        em.createNativeQuery("DELETE FROM log_fashion WHERE log_fashion_id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Optional<LogFashion> findLogFashion(Long logId, Long fashionItemId){
        try {
            LogFashion result = em.createQuery("select lf from LogFashion lf where lf.log.id=:logId and lf.fashionItem.id=:fashionItemId", LogFashion.class)
                    .setParameter("logId", logId)
                    .setParameter("fashionItemId", fashionItemId)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            // Handle non-unique result case if needed
            return Optional.empty();
        }
    }
}
