package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.fashion.category.entity.Category;
import WhereWear.server.wherewear.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class FashionItemRepository{
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public FashionItem save(FashionItem fashionItem) {
        em.persist(fashionItem);
        return fashionItem;
    }
    @Transactional
    public void delete(Long id){
        em.createNativeQuery("DELETE FROM fashion_item WHERE fashion_item_id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
