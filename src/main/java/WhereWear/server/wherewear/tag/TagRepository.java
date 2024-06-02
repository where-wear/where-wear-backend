package WhereWear.server.wherewear.tag;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.place.Place;
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
public class TagRepository {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    public Optional<Tag> findById(Long tagId){
        try {
            Tag result = em.createQuery("select t from Tag t where t.id=:tagId", Tag.class)
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
