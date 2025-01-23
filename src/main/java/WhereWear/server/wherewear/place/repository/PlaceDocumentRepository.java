package WhereWear.server.wherewear.place.repository;

import WhereWear.server.wherewear.place.domain.PlaceDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PlaceDocumentRepository extends ElasticsearchRepository<PlaceDocument, Long> {
    @Query("{\n" +
            "  \"match\": {\n" +
            "    \"search_field\": \"?0\"\n" +
            "  }\n" +
            "}")
    List<PlaceDocument> findPlaceByName(String placeName);

    // Category를 기준으로 가장 인기 있는 장소 찾기
    @Query("{ " +
            "  \"bool\": { " +
            "    \"must\": [ " +
            "      { \"term\": { \"category\": \"?0\" } } " +
            "    ] " +
            "  }, " +
            "  \"aggs\": { " +
            "    \"top_places\": { " +
            "      \"terms\": { \"field\": \"x,y\", \"size\": 3, \"order\": { \"_count\": \"desc\" } } " +
            "    } " +
            "  } " +
            "}")
    List<PlaceDocument> findTopPlaceByCategory(String category);
}
