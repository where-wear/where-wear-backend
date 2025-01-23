package WhereWear.server.wherewear.place.repository;

import WhereWear.server.wherewear.place.domain.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // PlaceName을 기준으로 장소 검색 (LIKE 검색)
    @Query("SELECT p FROM Place p WHERE LOWER(p.placeName) LIKE LOWER(CONCAT('%', :placeName, '%')) " +
            "GROUP BY p.placeName, p.x, p.y, p.id ORDER BY COUNT(p.id) DESC")
    List<Place> findPlaceByName(@Param("placeName") String placeName, Pageable pageable);


    // Category를 기준으로 가장 인기 있는 장소 찾기
    @Query("SELECT new Place(MAX(p.id), MAX(p.address), MAX(p.category), p.x, p.y, MAX(p.placeName)) FROM Place p " +
            "JOIN Log l ON p.id = l.place.id " +
            "WHERE p.category = :category " +
            "GROUP BY p.x, p.y " +
            "ORDER BY COUNT(l.id) DESC")
    List<Place> findTopPlaceByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Place p WHERE p.category = :category")
    Optional<Place> findPlaceByCategory(@Param("category") String category);
}
