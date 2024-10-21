package WhereWear.server.wherewear.log.explore;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExploreDto {
    private String nickname;
    private List<TopFashionLog> topFashionLogs;
    private List<TagTopPlace> tagTopPlaces;
    private List<String> hotKeywords;

    public ExploreDto(String nickname, List<TopFashionLog> topFashionLogs, List<TagTopPlace> tagTopPlaces, List<String> hotKeywords) {
        this.nickname = nickname;
        this.topFashionLogs = topFashionLogs;
        this.tagTopPlaces = tagTopPlaces;
        this.hotKeywords = hotKeywords;
    }
}
