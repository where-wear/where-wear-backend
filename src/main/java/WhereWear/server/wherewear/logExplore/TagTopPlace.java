package WhereWear.server.wherewear.logExplore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagTopPlace {
    private String placeName;
    private String imgUrl;

    public TagTopPlace(String placeName, String imgUrl){
        this.placeName = placeName;
        this.imgUrl = imgUrl;
    }
}
