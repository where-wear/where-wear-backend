package WhereWear.server.wherewear.place;

import WhereWear.server.wherewear.log.place.PlaceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceLogSummary {
    private int logCount;
    private PlaceDto place;

    public PlaceLogSummary(int logCount, Place place){
        this.logCount = logCount;
        this.place = new PlaceDto(place);
    }
}
