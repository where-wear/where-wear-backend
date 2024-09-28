package WhereWear.server.wherewear.place;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place addPlace(Double x, Double y, String address, String placeName) {
        String[] addressParts = address.split(" ");
        String category = addressParts[1];
        Place place = new Place(address,category,x,y,placeName);
        return place;
    }

    public List<Place> getTopTaggedPlaces(String category) {
        return placeRepository.findTopPlaceByCategory(category);
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }
}
