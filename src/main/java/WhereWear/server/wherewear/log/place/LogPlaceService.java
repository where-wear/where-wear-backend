package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class LogPlaceService {
    private final LogService logService;
    private final PlaceService placeService;

    public Log addPlaceToLog(Long logId, double x, double y, String address, String placeName ) {
        Log log = logService.findByLogId(logId);
        Place place = placeService.addPlace(x,y,address,placeName);

        log.setPlace(place);

        placeService.savePlace(place);
        return logService.saveLog(log);
    }

    public List<Object[]> countLogsByXY(double minX, double maxX, double minY, double maxY){
        return logService.countLogsByXY(minX, maxX, minY, maxY)
                .orElse(Collections.emptyList());
    }

}
