package WhereWear.server.wherewear.log.likedLog;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedDto {
    private Long logId;
    private boolean isLiked;
    private Long userId;
    private String userName;

    public LikedDto(Log log, boolean flag, User user){
        this.logId = log.getId();
        this.isLiked = flag;
        this.userId = user.getId();
        this.userName = user.getNickname();
    }
}
