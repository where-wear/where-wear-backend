package WhereWear.server.wherewear.likedLog.dto;

import WhereWear.server.wherewear.likedLog.domain.LikedLog;
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
