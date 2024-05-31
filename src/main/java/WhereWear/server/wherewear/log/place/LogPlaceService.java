package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class LogPlaceService {
    private final LogService logService;
    private final PlaceService placeService;
    public Log addPlaceToLog(Long logId, Double x, Double y,String roadAddress,String address,String placeName ) {
        Log log = logService.findByLogId(logId);
        Place place = placeService.addPlace(x,y,roadAddress,address,placeName);

        log.setPlace(place);

        placeService.savePlace(place);
        return logService.saveLog(log);
    }

    public Log deletePlaceToLog(Long id) {
        Log log = logService.findByLogId(id);
        Place place = log.getPlace();

        if (place != null) {
            log.removePlace(place);
            placeService.savePlace(place);
        }

        return logService.saveLog(log);
    }
}
