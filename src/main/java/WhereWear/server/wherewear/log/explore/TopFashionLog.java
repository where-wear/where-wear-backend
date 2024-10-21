package WhereWear.server.wherewear.log.explore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopFashionLog {

    private Long id;
    private String imgUrl;
    private boolean like;

    public TopFashionLog(Long id, String imgUrl, boolean like){
        this.id = id;
        this.imgUrl = imgUrl;
        this.like = like;
    }
}
