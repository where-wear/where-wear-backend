package WhereWear.server.wherewear.log.logImage;

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
public class LogImageRepository {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public LogImage save(LogImage logImage) {
        em.persist(logImage);
        return logImage;
    }

    public Optional<LogImage> findLogImageById(Long imageId){
        try {
            LogImage result = em.createQuery("select li from LogImage li where li.id=:imageId", LogImage.class)
                    .setParameter("imageId", imageId)
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
