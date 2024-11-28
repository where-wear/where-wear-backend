package WhereWear.server.wherewear.place;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Place> findPlaceByName(String placeName) {
        String searchKeyword = "%" + placeName.trim().toLowerCase() + "%";
        List<Place> result = em.createQuery(
                        "SELECT p " +
                                "FROM Place p " +
                                "WHERE p.id IN (" +
                                "   SELECT MAX(p2.id) " +
                                "   FROM Place p2 " +
                                "   WHERE LOWER(p2.placeName) LIKE LOWER(CONCAT('%', :placeName, '%')) " +
                                "   GROUP BY p2.x, p2.y" +
                                ") " +
                                "ORDER BY (SELECT COUNT(p3) FROM Place p3 WHERE p3.x = p.x AND p3.y = p.y) DESC",
                        Place.class)
                .setParameter("placeName", searchKeyword)
                .setMaxResults(20)
                .getResultList();

        return result;
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
