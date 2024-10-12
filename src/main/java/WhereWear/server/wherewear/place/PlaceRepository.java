package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Place> findTopPlaceByCategory(String category) {
        List<Place> result = em.createQuery(
                        "SELECT new Place(MIN(p.id), MIN(p.address), MIN(p.category), p.x, p.y, MIN(p.placeName)) " +
                                "FROM Place p " +
                                "JOIN Log l ON p.id = l.place.id " +
                                "WHERE p.category = :category " +
                                "GROUP BY p.x, p.y " +
                                "ORDER BY COUNT(l.id) DESC", Place.class)
                .setParameter("category", category)
                .setMaxResults(3)
                .getResultList();
        return result;
    }



}
