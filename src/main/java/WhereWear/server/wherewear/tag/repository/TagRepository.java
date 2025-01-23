package WhereWear.server.wherewear.tag.repository;

import WhereWear.server.wherewear.tag.domain.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<String> findHotKeywords(String category){
        List<String> result = em.createQuery(
                "SELECT t.tagName " +
                        "FROM Tag t " +
                        "JOIN t.log l " +
                        "JOIN l.place p " +
                        "WHERE p.category = :category " +
                        "GROUP BY t.tagName " +
                        "ORDER BY COUNT(l.id) DESC", String.class)
                .setParameter("category", category)
                .setMaxResults(3)
                .getResultList();

        return result;
    }

}
