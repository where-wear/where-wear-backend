package WhereWear.server.wherewear.place;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceDataSyncService {

    private final PlaceRepository entityRepository;
    private final PlaceDocumentRepository documentRepository;

    public PlaceDataSyncService(PlaceRepository entityRepository, PlaceDocumentRepository documentRepository) {
        this.entityRepository = entityRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public void syncDataToElasticsearch() {
        List<Place> entities = entityRepository.findAll();

        List<PlaceDocument> documents = entities.stream()
                .map(PlaceDocument::from)
                .collect(Collectors.toList());

        documentRepository.saveAll(documents);
    }
}