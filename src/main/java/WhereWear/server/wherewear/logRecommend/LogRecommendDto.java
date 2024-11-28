package WhereWear.server.wherewear.logRecommend;

import WhereWear.server.wherewear.log.domain.Log;
import lombok.Getter;

@Getter
public class LogRecommendDto {
    private Long id;
    private String imgUrl;

    public LogRecommendDto(Log log){
        this.id = log.getId();
        this.imgUrl = log.getLogImages().get(0).getPublicUrl();
    }
}
