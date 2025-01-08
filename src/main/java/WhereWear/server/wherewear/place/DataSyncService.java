package WhereWear.server.wherewear.place;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataSyncService {

    private final PlaceRepository entityRepository;
    private final PlaceDocumentRepository documentRepository;

    public DataSyncService(PlaceRepository entityRepository, PlaceDocumentRepository documentRepository) {
        this.entityRepository = entityRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public void syncDataToElasticsearch() {
        List<Place> entities = entityRepository.findAll();

        List<PlaceDocument> documents = entities.stream().map(entity -> {
            PlaceDocument document = new PlaceDocument();
            document.setId(entity.getId());
            document.setPlaceName(entity.getPlaceName());
            document.setAddress(entity.getAddress());
            document.setCategory(entity.getCategory());
            document.setX(entity.getX());
            document.setY(entity.getY());
            return document;
        }).collect(Collectors.toList());

        documentRepository.saveAll(documents);
    }
}