package WhereWear.server.wherewear.log.explore;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepository;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.log.likedLog.LikedLog;
import WhereWear.server.wherewear.log.likedLog.LikedLogService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceService;
import WhereWear.server.wherewear.tag.TagService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExploreService {

    private final UserService userService;
    private final LikedLogService likedLogService;
    private final PlaceService placeService;
    private final TagService tagService;
    private final LogService logService;

    public ExploreDto explore(String token, String category){
        List<TopFashionLog> topFashionLogs = getTopLogs(token, category);
        List<TagTopPlace> tagTopPlaces = getTagTopPlace(category);
        List<String> hotKeyWords = getHotKeywords(category);
        String nickname = "";
        if (token != null && !token.isEmpty()) {
            nickname = userService.findByAccessToken(token).getNickname();
        }
        return new ExploreDto(nickname, topFashionLogs, tagTopPlaces, hotKeyWords);
    }

    public List<TopFashionLog> getTopLogs(String token, String category) {
        List<TopFashionLog> topFashionLogs = new ArrayList<>();
        List<Log> topLogs = likedLogService.getTopLogs();

        if (token != null && !token.isEmpty()) {
            User user = userService.findByAccessToken(token);
            for (Log log : topLogs) {
                boolean like = false;
                for (LikedLog likedLog : log.getLikedLogs()) {
                    if (likedLog.getUser().getEmail().equals(user.getEmail())) {
                        like = true;
                        break;
                    }
                }
                topFashionLogs.add(new TopFashionLog(log.getId(), log.getLogImages().get(0).getPublicUrl(), like));
            }
        } else {
            for (Log log : topLogs) {
                topFashionLogs.add(new TopFashionLog(log.getId(), log.getLogImages().get(0).getPublicUrl(), false));
            }
        }

        return topFashionLogs;
    }

    public List<TagTopPlace> getTagTopPlace(String category){
        List<TagTopPlace> tagTopPlaces = new ArrayList<>();
        for(Place place : placeService.getTopTaggedPlaces(category)){
            String logImage = logService.findByPlace(place).getLogImages().get(0).getPublicUrl();
            tagTopPlaces.add(new TagTopPlace(place.getPlaceName(), logImage));
        }
        return tagTopPlaces;
    }

    public List<String> getHotKeywords(String category){
        return tagService.getHotKeywords(category);
    }

}
