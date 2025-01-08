package WhereWear.server.wherewear.place;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // PlaceName을 기준으로 장소 검색 (LIKE 검색)
    @Query("SELECT p FROM Place p WHERE LOWER(p.placeName) LIKE LOWER(CONCAT('%', :placeName, '%')) " +
            "GROUP BY p.x, p.y ORDER BY COUNT(p.id) DESC")
    List<Place> findPlaceByName(@Param("placeName") String placeName, Pageable pageable);

    // Category를 기준으로 가장 인기 있는 장소 찾기
    @Query("SELECT p FROM Place p " +
            "JOIN Log l ON p.id = l.place.id " +
            "WHERE p.category = :category " +
            "GROUP BY p.x, p.y " +
            "ORDER BY COUNT(l.id) DESC")
    List<Place> findTopPlaceByCategory(@Param("category") String category, Pageable pageable);
}
