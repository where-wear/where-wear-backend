package WhereWear.server.wherewear.log.tag;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.tag.Tag;
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
public class LogTagRepository {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public LogTag save(LogTag logTag) {
        em.persist(logTag);
        return logTag;
    }
    @Transactional
    public void deleteById(Long logTagId) {
        em.createNativeQuery("DELETE FROM log_tag WHERE log_tag_id = :logTagId")
                .setParameter("logTagId", logTagId)
                .executeUpdate();
    }

    public Optional<LogTag> findLogTag(Long logId, Long tagId){
        try {
            LogTag result = em.createQuery("select lt from LogTag lt where lt.log.id=:logId and lt.tag.id=:tagId", LogTag.class)
                    .setParameter("logId", logId)
                    .setParameter("tagId", tagId)
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
