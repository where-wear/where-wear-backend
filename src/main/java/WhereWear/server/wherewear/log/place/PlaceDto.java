package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.fashion.category.dto.CategoryDto;
import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.place.Place;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class PlaceDto {

    private Long id;
    private String roadAddress;
    private String address;
    private Double x;
    private Double y;
    private String placeName;

    public PlaceDto(Place place) {
        this.id = place.getId();
        this.roadAddress = place.getRoadAddress();
        this.address = place.getAddress();
        this.x = place.getX();
        this.y = place.getY();
        this.placeName = place.getPlaceName();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("roadAddress", roadAddress)
                .append("address", address)
                .append("x", x)
                .append("y", y)
                .append("placeName", placeName)
                .toString();
    }
}
