package WhereWear.server.wherewear.place.service;

import WhereWear.server.wherewear.place.domain.Place;
import WhereWear.server.wherewear.place.domain.PlaceDocument;
import WhereWear.server.wherewear.place.dto.PlaceDocumentDto;
import WhereWear.server.wherewear.place.repository.PlaceDocumentRepository;
import WhereWear.server.wherewear.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceDocumentRepository placeDocumentRepository;

    public List<PlaceDocumentDto> searchPlaceByName(String placeName) {
        /*Pageable pageable = PageRequest.of(0, 20);
        return placeRepository.findPlaceByName(placeName, pageable)
                .stream()
                .map(PlaceDto::new)
                .toList();*/
        return placeDocumentRepository.findPlaceByName(placeName).stream()
                .map(PlaceDocumentDto::new)
                .toList();
    }

    public Place createPlace(Double x, Double y, String address, String placeName) {
        String[] addressParts = address.split(" ");
        String category = addressParts[1];
        return placeRepository.save(Place.of(address, category, x, y, placeName));
    }

    public List<Place> getTopTaggedPlaces(String category) {
        Pageable pageableForCategory = PageRequest.of(0, 3);
        return placeRepository.findTopPlaceByCategory(category, pageableForCategory);
    }

    public Place getPlaceByCategory(String category) {
        return placeRepository.findPlaceByCategory(category)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected category"));
    }
}
