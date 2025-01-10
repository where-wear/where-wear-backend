package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.logPlace.PlaceDto;
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

    public Place addPlace(Double x, Double y, String address, String placeName) {
        String[] addressParts = address.split(" ");
        String category = addressParts[1];
        Place place = new Place(address, category, x, y, placeName);
        return place;
    }

    public List<Place> getTopTaggedPlaces(String category) {
        Pageable pageableForCategory = PageRequest.of(0, 3);
        return placeRepository.findTopPlaceByCategory(category, pageableForCategory);
    }

    public Place savePlace(Place place) {
        Place savedPlace = placeRepository.save(place);
        placeDocumentRepository.save(PlaceDocument.from(savedPlace));
        return savedPlace;
    }
}
