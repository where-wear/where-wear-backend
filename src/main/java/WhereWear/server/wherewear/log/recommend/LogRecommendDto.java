package WhereWear.server.wherewear.log.recommend;

import WhereWear.server.wherewear.log.Log;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class LogRecommendDto {
    private Long id;
    private String imgUrl;
    private int height;
    private int weight;
    private int footSize;
    private String job;

    public LogRecommendDto(Log log){
        this.id = log.getId();
        this.imgUrl = log.getLogImages().get(0).getPublicUrl();
        this.height = log.getUser().getHeight();
        this.weight = log.getUser().getWeight();
        this.footSize = log.getUser().getFootSize();
        this.job = log.getUser().getJob();
    }
}
