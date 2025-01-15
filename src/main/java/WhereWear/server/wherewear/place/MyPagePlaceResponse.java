package WhereWear.server.wherewear.place;

import lombok.Getter;

@Getter
public class MyPagePlaceResponse {
    private String placeName;

    public MyPagePlaceResponse(Place place) {
        this.placeName = place.getPlaceName();
    }
}
