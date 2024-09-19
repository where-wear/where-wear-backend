package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepository;
import WhereWear.server.wherewear.log.LogRepositoryCustom;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.log.place.LogPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place addPlace(double x, double y, String roadAddress, String address, String placeName) {
        Place place = new Place(roadAddress,address,x,y,placeName);
        return place;
    }

    public Place deletePlace(double x, double y, String roadAddress, String address, String placeName) {
        Place place = new Place(roadAddress,address,x,y,placeName);
        return place;
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }
}
