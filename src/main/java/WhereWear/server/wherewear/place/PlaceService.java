package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepository;
import WhereWear.server.wherewear.log.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    public Place addPlace(Double x, Double y, String address, String placeName) {
        Place place = new Place(address,x,y,placeName);
        return place;
    }

    public Place deletePlace(Double x, Double y, String address, String placeName) {
        Place place = new Place(address,x,y,placeName);
        return place;
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }
}
