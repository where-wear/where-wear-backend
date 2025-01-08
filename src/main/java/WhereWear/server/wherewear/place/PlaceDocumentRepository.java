package WhereWear.server.wherewear.place;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PlaceDocumentRepository extends ElasticsearchRepository<PlaceDocument, Long> {
}
