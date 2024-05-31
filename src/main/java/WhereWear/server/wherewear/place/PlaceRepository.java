package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public Place save(Place place) {
        em.persist(place);
        return place;
    }

}
