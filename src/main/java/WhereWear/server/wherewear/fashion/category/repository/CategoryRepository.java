package WhereWear.server.wherewear.fashion.category.repository;

import WhereWear.server.wherewear.fashion.category.entity.Category;
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
public class CategoryRepository {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Category save(Category category) {
        em.persist(category);
        return category;
    }

    public Optional<Category> findById(Long categoryId){
        try {
            Category result = em.createQuery("select c from Category c where c.id=:categoryId", Category.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            // Handle non-unique result case if needed
            return Optional.empty();
        }
    }

    public Optional<List<Category>> findByName(String categoryName){
        try {
            List<Category> result = em.createQuery(
                            "SELECT c FROM Category c where c.categoryName like :categoryName", Category.class)
                    .setParameter("categoryName", categoryName)
                    .getResultList();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            // Handle non-unique result case if needed
            return Optional.empty();
        }
    }



}
