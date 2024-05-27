package WhereWear.server.wherewear.fashion.category.repository;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.refreshToken.RefreshToken;
import WhereWear.server.wherewear.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class CategoryRespository {
    @PersistenceContext
    private final EntityManager em;

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
