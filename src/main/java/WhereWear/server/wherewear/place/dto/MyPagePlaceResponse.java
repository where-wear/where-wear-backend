package WhereWear.server.wherewear.place.dto;

import WhereWear.server.wherewear.place.domain.Place;
import lombok.Getter;

@Getter
public class MyPagePlaceResponse {
    private String placeName;
    private String address;

    public MyPagePlaceResponse(Place place) {
        this.placeName = place.getPlaceName();
        this.address = place.getAddress();
    }
}
