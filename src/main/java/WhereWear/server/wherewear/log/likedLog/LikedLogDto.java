package WhereWear.server.wherewear.log.likedLog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedLogDto {
    private Long userId;
    private String userName;
    public LikedLogDto(LikedLog likedLog){
        this.userId = likedLog.getUser().getId();
        this.userName = likedLog.getUser().getNickname();
    }
}
