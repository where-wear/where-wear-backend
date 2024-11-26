package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<Place> findPlaceByName(String placeName) {
        String searchKeyword = "%" + placeName.trim().toLowerCase() + "%";
        List<Object[]> rawResults = em.createQuery(
                        "SELECT p.x, p.y, GROUP_CONCAT(p.placeName) AS combinedNames " +
                                "FROM Place p " +
                                "WHERE LOWER(p.placeName) LIKE LOWER(CONCAT('%', :placeName, '%')) " +
                                "GROUP BY p.x, p.y " +
                                "ORDER BY COUNT(p) DESC", Object[].class)
                .setParameter("placeName", searchKeyword)
                .setMaxResults(20)
                .getResultList();

        List<Place> result = new ArrayList<>();
        for (Object[] row : rawResults) {
            Double x = (Double) row[0];
            Double y = (Double) row[1];
            String combinedNames = (String) row[2];
            Place mergedPlace = new Place();
            mergedPlace.setX(x);
            mergedPlace.setY(y);
            mergedPlace.setPlaceName(combinedNames); // 여러 이름을 하나로 합친 값
            result.add(mergedPlace);
        }

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
